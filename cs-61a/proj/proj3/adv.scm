;; ADV.SCM
;; This file contains the definitions for the objects in the adventure
;; game and some utility procedures.

(define-class (place name)
  (instance-vars
   (directions-and-neighbors '())
   (things '())
   (people '())
   (entry-procs '())
   (exit-procs '()))
  (method (type) 'place)
  (method (neighbors) (map cdr directions-and-neighbors))
  (method (exits) (map car directions-and-neighbors))
  (method (look-in direction)
    (let ((pair (assoc direction directions-and-neighbors)))
      (if (not pair)
	  '()                     ;; nothing in that direction
	  (cdr pair))))           ;; return the place object
  (method (appear new-thing)
    (if (memq new-thing things)
	(error "Thing already in this place" (list name new-thing)))
    (set! things (cons new-thing things))
    'appeared)
  (method (enter new-person)
    (if (memq new-person people)
	(error "Person already in this place" (list name new-person)))

    (for-each (lambda (p)(ask p 'notice new-person)) people) ;;;;; A4(a)
    
    (set! people (cons new-person people))
    (for-each (lambda (proc) (proc)) entry-procs) 
   
    'appeared)
  (method (gone thing)
    (if (not (memq thing things))
	(error "Disappearing thing not here" (list name thing)))
    (set! things (delete thing things)) 
    'disappeared)
  (method (exit person)
    (for-each (lambda (proc) (proc)) exit-procs)
    (if (not (memq person people))
	(error "Disappearing person not here" (list name person)))
    (set! people (delete person people)) 
    'disappeared)

  (method (new-neighbor direction neighbor)
    (if (assoc direction directions-and-neighbors)
	(error "Direction already assigned a neighbor" (list name direction)))
    (set! directions-and-neighbors
	  (cons (cons direction neighbor) directions-and-neighbors))
    'connected)

  (method (may-enter? person) #t) ;;;;; A4(b)

  (method (add-entry-procedure proc)
    (set! entry-procs (cons proc entry-procs)))
  (method (add-exit-procedure proc)
    (set! exit-procs (cons proc exit-procs)))
  (method (remove-entry-procedure proc)
    (set! entry-procs (delete proc entry-procs)))
  (method (remove-exit-procedure proc)
    (set! exit-procs (delete proc exit-procs)))
  (method (clear-all-procs)
    (set! exit-procs '())
    (set! entry-procs '())
    'cleared) )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Implementation of LOCKED-PLACE for part A4(b)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define-class (locked-place name)
  (instance-vars (open? #f))
  (parent (place name))
  (method (may-enter? person)
	  (if (eq? open? #t)
	      #t
	      #f))
  (method (unlock)(set! open? #t)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Implementation of GARAGE for part A5
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define-class (garage name)
  (instance-vars (ticket-table (make-table)))
  (class-vars (ticket-counter 0))
  (parent (place name))
  (method (park vehicle)
	  (if (memq vehicle (ask self 'things))
	      (begin
		(let ((garage-ticket (instantiate ticket ticket-counter))
		      (owner (ask vehicle 'possessor)))
		  (ask self 'appear garage-ticket)
		  (insert! (ask garage-ticket 'serial-number) vehicle ticket-table)
		  (ask owner 'lose vehicle)
		  (ask owner 'take garage-ticket)
		  (set! ticket-counter (+ ticket-counter 1))))
	       (print "Your car is not in the garbage")))
  (method (unpark garage-ticket)
	  (if (eq? (ask garage-ticket 'name) 'ticket)
	      (begin
		(let ((vehicle (lookup (ask garage-ticket 'serial-number) ticket-table))
		      (owner (ask garage-ticket 'possessor)))
		  (ask owner 'lose garage-ticket)
		  (ask owner 'take vehicle)
		  (insert! #f vehicle ticket-table))))))


(define-class (ticket serial-number)
  (parent (thing 'ticket)))

(define (pick-ticket person)
  (car (filter (lambda (item)(eq? (ask item 'name) 'ticket))(ask person 'possessions))))
      

; (ask mufasa 'take robot-unicorn) 
; (ask mufasa 'go 'east)
; (ask harmony-harmony-harmony 'park robot-unicorn)

(define-class (person name place)
  (instance-vars
   (possessions '())
   (saying ""))
  (initialize
   (ask place 'enter self))
  (method (type) 'person)
  (method (look-around)
    (map (lambda (obj) (ask obj 'name))
	 (filter (lambda (thing) (not (eq? thing self)))
		 (append (ask place 'things) (ask place 'people)))))
  (method (take thing)
    (cond ((not (thing? thing)) (error "Not a thing" thing))
	  ((not (memq thing (ask place 'things)))
	   (error "Thing taken not at this place"
		  (list (ask place 'name) thing)))
	  ((memq thing possessions) (error "You already have it!"))
	  (else
	   (announce-take name thing)
	   (set! possessions (cons thing possessions))
	       
	   ;; If somebody already has this object...
	   (for-each
	    (lambda (pers)
	      (if (and (not (eq? pers self)) ; ignore myself
		       (memq thing (ask pers 'possessions)))
		  (begin
		   (ask pers 'lose thing)
		   (have-fit pers))))
	    (ask place 'people))
	       
	   (ask thing 'change-possessor self)
	   'taken)))

  (method (lose thing)
    (set! possessions (delete thing possessions))
    (ask thing 'change-possessor 'no-one)
    'lost)
  (method (talk) (print saying))
  (method (set-talk string) (set! saying string))
  (method (exits) (ask place 'exits))
  (method (notice person) (ask self 'talk))
  (method (go direction)
    (let ((new-place (ask place 'look-in direction)))
      (cond ((null? new-place)
	     (error "Can't go" direction))
	    ((eq? (ask new-place 'may-enter? self) #f) ;;;;; A4(b)
	     (error "The light does not touch this part of the kingdom"))
	    (else
	     (ask place 'exit self)
	     (announce-move name place new-place)
	     (for-each
	      (lambda (p)
		(ask place 'gone p)
		(ask new-place 'appear p))
	      possessions)
	     (set! place new-place)
	     (ask new-place 'enter self))))) )

(define thing
  (let ()
    (lambda (class-message)
      (cond
       ((eq? class-message 'instantiate)
	(lambda (name)
	  (let ((self '()) (possessor 'no-one))
	    (define (dispatch message)
	      (cond
	       ((eq? message 'initialize)
		(lambda (value-for-self)
		  (set! self value-for-self)))
	       ((eq? message 'send-usual-to-parent)
		(error "Can't use USUAL without a parent." 'thing))
	       ((eq? message 'name) (lambda () name))
	       ((eq? message 'possessor) (lambda () possessor))
	       ((eq? message 'type) (lambda () 'thing))
	       ((eq? message 'change-possessor)
		(lambda (new-possessor)
		  (set! possessor new-possessor)))
	       (else (no-method 'thing))))
	    dispatch)))
       (else (error "Bad message to class" class-message))))))




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Implementation of thieves for part two
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define *foods* '(pizza potstickers coffee))

(define (edible? thing)
  (member? (ask thing 'name) *foods*))

(define-class (thief name initial-place)
  (parent (person name initial-place))
  (instance-vars
   (behavior 'steal))
  (method (type) 'thief)

  (method (notice person)
    (if (eq? behavior 'run)
	(ask self 'go (pick-random (ask (usual 'place) 'exits)))
	(let ((food-things
	       (filter (lambda (thing)
			 (and (edible? thing)
			      (not (eq? (ask thing 'possessor) self))))
		       (ask (usual 'place) 'things))))
	  (if (not (null? food-things))
	      (begin
	       (ask self 'take (car food-things))
	       (set! behavior 'run)
	       (ask self 'notice person)) )))) )

;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Utility procedures
;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;; this next procedure is useful for moving around

(define (move-loop who)
  (newline)
  (print (ask who 'exits))
  (display "?  > ")
  (let ((dir (read)))
    (if (equal? dir 'stop)
	(newline)
	(begin (print (ask who 'go dir))
	       (move-loop who)))))


;; One-way paths connect individual places.

(define (can-go from direction to)
  (ask from 'new-neighbor direction to))


(define (announce-take name thing)
  (newline)
  (display name)
  (display " took ")
  (display (ask thing 'name))
  (newline))

(define (announce-move name old-place new-place)
  (newline)
  (newline)
  (display name)
  (display " moved from ")
  (display (ask old-place 'name))
  (display " to ")
  (display (ask new-place 'name))
  (newline))

(define (have-fit p)
  (newline)
  (display "Yaaah! ")
  (display (ask p 'name))
  (display " is upset!")
  (newline))

(define (pick-random set)
  (nth (random (length set)) set))

(define (delete thing stuff)
  (cond ((null? stuff) '())
	((eq? thing (car stuff)) (cdr stuff))
	(else (cons (car stuff) (delete thing (cdr stuff)))) ))

(define (person? obj)
  (and (procedure? obj)
       (member? (ask obj 'type) '(person police thief))))

(define (thing? obj)
  (and (procedure? obj)
       (eq? (ask obj 'type) 'thing)))

(define (whereis person) ;;;;; 2F. Where is the person located?
  (ask (ask person 'place) 'name))

(define (owner thing) ;;;;; 2F. Who owns the thing?
  (if (eq? (ask thing 'possessor) 'no-one)
      'no-one
      (ask (ask thing 'possessor) 'name)))


;;;;; #2


;; 2A: Brian is procedure, an object.

;; 2B: Messages a PLACE understands:

;; (ask place 'name)
;;            'directions-and-neighbors
;;            'things
;;            'people
;;            'entry-procs
;;            'exit-procs
;;            'type
;;            'neighbors
;;            'exits
;;            'look-in <direction>
;;            'appear <new-thing>
;;            'enter <new-person>
;;            'gone <thing>
;;            'exit <person>
;;            'new-neighbor <direction> <neighbor>
;;            'add-entry-procedure <proc>
;;            'add-exit-procedure <proc>
;;            'remove-entry-procedure <proc>
;;            'remove-exit-procedure <proc>
;;            'clear-all-procs

;; 2C: If we type in (ask Brian 'place), we get a procedure of the place. In order to get the place name, we have to ask it for its name. That is, we have to type in (ask (ask Brian 'place) 'name).

;; 2C: When we ask a PLACE to 'appear <thing>, we are really just asking the place to "add" the <thing> to its list of things (instance-variable). The 'appear method does that and then returns the message "appeared" when it adds the <thing> successfully. However, peoples-park is not given a name in STk, so we cannot use it as an object and ask it to 'appear things.

;; 2D: The correct way to ask 61a-lab to appear Durer is (ask 61a-lab appear computer). We cannot ask 61a-lab to 'appear Durer or 'Durer because those are not objects. Durer is not defined (we could rename computer to Durer though), and 'Durer is just a string. When we do (computer 'name), computer is the dispatch procedure, and it takes the message 'name. It then returns (lambda () name), which is a procedure.

;; 2E:

(define (oop-thing name)
  (instance-vars
   (possessor 'no-one))
  (method (type) 'thing)
  (method (name)(ask self 'name))
  (method (possesor)(ask self 'possessor))
  (method (change-possessor new-possessor)
	  (set! possessor new-possessor)))

;; 2F:

; (define (whereis person) ;;;;; 2F. Where is the person located?
;   (ask (ask person 'place) 'name))

; (define (owner thing) ;;;;; 2F. Who owns the thing?
;   (if (eq? (ask thing 'possessor) 'no-one)
;       'no-one
;       (ask (ask thing 'possessor) 'name))
