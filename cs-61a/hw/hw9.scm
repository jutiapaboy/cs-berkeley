;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen

;; 3.16


;; To make it return three:(list 'a 'b 'c)
;; Then count-pairs returns 3

;; [a|-]-> [b|-]-> [c|-]->

;; To make it return four: (define a (list 'a 'b 'c))
;; (set-car! a (cddr a))
;; a: ((c) b c)

;; [| | -]-> [b|-]-> [c|-]->
;;  ------------------^

;; To make it return seven: (define b (list 'a 'b 'c))
;; (set-car! b (cdr b))
;; (set-cdr! (car b) (cddr b))
;; b: (((c) c) (c) c)

;;  [||-]-> [||-]-> [c|-]->
;;   --^     --------^

;; To make it go to infinity: (define c (list 'a 'b 'c))
;; (set-cdr! d d)
;; c: (a a a a a a . . .

;; [a | | ]
;;  ^---

;; 3.17

;; (define auxiliary '())

(define (counted? x l)
  (cond ((empty? l) #f)
	((eq? x (car l)) #t)
	(else (counted? x  (cdr l)))) )

(define (count-pairs x)
  (let ((auxiliary '()))
    
(define (count-pairs-helper x)
  (cond ((not (pair? x)) 0)
	((counted? x auxiliary) 0)
	(else (set! auxiliary (cons x auxiliary))
	      (+ 1 (count-pairs-helper (car x))
		   (count-pairs-helper (cdr x))))))

    (count-pairs-helper x)))

;; 3.21

(define (make-queue) (cons '() '()))

(define (insert-queue! queue item)
  (let ((new-pair (cons item '())))
    (cond ((empty-queue? queue)
           (set-front-ptr! queue new-pair)
           (set-rear-ptr! queue new-pair)
           queue)
          (else
           (set-cdr! (rear-ptr queue) new-pair)
           (set-rear-ptr! queue new-pair)
           queue))))

(define (empty-queue? queue) (null? (front-ptr queue)))

(define (front-queue queue)
  (if (empty-queue? queue)
      (error "FRONT called with an empty queue" queue)
      (car (front-ptr queue))))

(define (front-ptr queue) (car queue))
(define (rear-ptr queue) (cdr queue))
(define (set-front-ptr! queue item) (set-car! queue item))
(define (set-rear-ptr! queue item) (set-cdr! queue item))

(define (delete-queue! queue)
  (cond ((empty-queue? queue)
         (error "DELETE! called with an empty queue" queue))
        (else
         (set-front-ptr! queue (cdr (front-ptr queue)))
         queue)))

;; The queue prints that way because insert-queue only changes the rear pointer, and delete-queue changes only the front pointer. The first element of the result that is printer is what the front pointer points to. The second element points to the end of what the first pointer points to. We can define print-queue by just taking the car of the queue:

(define (print-queue q)
  (car q))

;; 3.25

(define (make-table1) (list '*table*))

(define (insert!1 value table . keys)
  (if (not (null? keys))
      (let ((record (assoc (car keys) (cdr table))))
        (if (not record)
            (begin
              (set! record (cons (car keys) nil))
              (set-cdr! table (cons record (cdr table)))))
        (if (null? (cdr keys))
            (set-cdr! record value)
            (apply insert!
                   (cons value
                         (cons record (cdr keys))))))))

(define (lookup1 table . keys)
  (if (null? keys) #f
      (let ((record (assoc (car keys) (cdr table))))
        (if record
            (if (null? (cdr keys))
                (cdr record)
                (apply lookup (cons record (cdr keys))))
            #f))))

;; 3.27

(define (memoize f)
  (let ((table (make-table)))
    (lambda (x)
      (let ((previously-computed-result (lookup x table)))
        (or previously-computed-result
            (let ((result (f x)))
              (insert! x result table)
              result))))))

(define memo-fib
  (memoize (lambda (n)
             (cond ((= n 0) 0)
                   ((= n 1) 1)
                   (else (+ (memo-fib (- n 1))
                            (memo-fib (- n 2))))))) )

;; Memo-fib remembers the number of previous calls to itself. Since we already have some numbers memorized, we can simply use them to compute the next Fibonacci number. The regular fib has order of growth of (2^n) since nothing is memorized. We cannot simply do (memoize fib) since it doesn't remember any of its previous results.

;; 1

(define (vector-append vec1 vec2)
  (define (loop nv vec n i)
    (if (>= n 0)
      (begin (vector-set! nv i (vector-ref vec n))
             (loop nv vec (- n 1) (- i 1)))))
  (let ((newvec (make-vector (+ (vector-length vec1) (vector-length vec2)))))
    (loop newvec vec1 (- (vector-length vec1) 1) (- (vector-length vec1) 1))
    (loop newvec vec2 (- (vector-length vec2) 1) (- (vector-length newvec) 1))
    newvec))

;; 2

(define (vector-filter pred vec)
  (define (loop n)
    (cond ((< n 0)(make-vector 0))
	  ((pred (vector-ref vec n))
	   (vector-append (loop (- n 1))(make-vector 1 (vector-ref vec n))))
	  (else (vector-append (loop (- n 1))(make-vector 0)))))
  (loop (- (vector-length vec) 1)))
