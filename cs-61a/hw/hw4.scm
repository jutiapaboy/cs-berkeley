;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen

;;;;;; Homework #4

;;;;; #1

;; 2.7
;; Define selectsr upper-bound and lower-bound

(define (make-interval a b)(cons a b))

(define (upper-bound int)(cdr int))
(define (lower-bound int)(car int))

;; 2.8 sub-interval
;; Subtract two intervals

(define (sub-interval x y)
  (let ((p1 (max (upper-bound x)(upper-bound y)))
	(p2 (min (upper-bound x)(upper-bound y)))
	(p3 (max (lower-bound x)(lower-bound y)))
	(p4 (min (lower-bound x)(lower-bound y))))
    (make-interval (- p3 p4)(- p1 p2))))

;; 2.10

(define (mul-interval x y)
  (let ((p1 (* (lower-bound x) (lower-bound y)))
        (p2 (* (lower-bound x) (upper-bound y)))
        (p3 (* (upper-bound x) (lower-bound y)))
        (p4 (* (upper-bound x) (upper-bound y))))
    (make-interval (min p1 p2 p3 p4)
                   (max p1 p2 p3 p4))))

(define (div-interval x y)
  (if (and (>= (upper-bound x) 0)
           (<= (lower-bound x) 0))
      "You are dividing by an interval that spans zero."
      (mul-interval x (make-interval (/ 1.0 (upper-bound y))
				     (/ 1.0 (lower-bound y))))))

;; 2.12

(define (make-center-width c w)
  (make-interval (- c w) (+ c w)))

(define (center i)
  (/ (+ (lower-bound i) (upper-bound i)) 2))
(define (width i)
  (/ (- (upper-bound i) (lower-bound i)) 2))

(define (make-center-percent c tol)
  (make-center-width c (* c tol)))

(define (percent int)
  (abs (- (car int)(center int))))

;; 2.17 last-pair

(define (last-pair l)
  (if (null? (cdr l))
      l
      (last-pair (cdr l))))

;; 2.20

; (define (same-parity x . z)
;   (cond ((null? z)
; 	 ...


;; 2.22

;; When the program does (cons (square (car things))), it takes an element and adds it to the list. However, since the first elements are the first arguments toin the list, they get "pushed" back to the end by the remaining elements.

;; Same argument as above. The first elements get con'ed to the back of the list.

;; 2.23

(define (for-each proc args)
  (if (not (null? args))
      (and (proc (car args))
	   (for-each proc (cdr args)))
       " "))
          
;;;;; #2

;; substitute

(define (substitute l old new)
  (cond ((null? l) '())
	((pair? (car l))(cons (substitute (car l) old new)(substitute (cdr l) old new)))
	((equal? old (car l))(cons new (substitute (cdr l) old new)))
	(else (cons (car l) (substitute (cdr l) old new)))))


;; substitute2

;(define (substitute2 l l-old l-new)
;  (cond ((null? l) '())
;	((pair? (car l))(cons (substitute2 (car l) l-old l-new)(substitute2 (cdr l) l-old l-new)))
;	((member? (car l)(l-old)) ... 
