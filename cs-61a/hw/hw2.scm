;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen

;;;;;  #1

;; 1.31(a) product
;; input: a, b, integers
;; output: product of integers from a to b

(define (product a b)
  (if (> a b)
      1
      (* a (product (+ a 1) b))))


(define (factorial n)
  (product 1 n))

(define (productn a b)
  (if (> a b)
      1
      (* a (productn (+ a 2) b))))

(define (two-times a b) (* (productn a b)(productn a b)))

(define pi (/ (* 2 4 (two-times 4 98) 100)(two-times 1 99)))


;; 1.32(a) accumulate
;; input: combiner, null-value, term, a, next, b
;; output: uses combiner to combines terms of the interval [a, b]

(define (accumulate combiner null-value term a next b)
  (if (> a b)
      null-value
      (combiner (term a)
		(accumulate combiner null-value term (next a) next b))))

;; 1.33 filtered-accumulate
;; input: same as "accumulate", but with an additional predicate as one of the arguments
;; output: accumulate but only terms that satisfy the predicate are combined


(define (filtered-accumulate predicate combiner null-value term a next b)
  (if (> a b)
      null-value
      (if (predicate a)
	  (combiner (term a)
		    (filtered-accumulate predicate combiner null-value term (next a) next b))
	  (filtered-accumulate predicate combiner null-value term (next a) next b))))

(define (increase n)(+ n 1))

;; 1.40 cubic
;; input: a, b, c, three numbers
;; output: f(x) = x^3 + ax^2 + bx + c

(define (newton-transform g)
  (lambda (x)
    (- x (/ (g x) ((deriv g) x)))))
(define (newtons-method g guess)
  (fixed-point (newton-transform g) guess))

(define tolerance 0.00001)
(define (fixed-point f first-guess)
  (define (close-enough? v1 v2)
    (< (abs (- v1 v2)) tolerance))
  (define (try guess)
    (let ((next (f guess)))
      (if (close-enough? guess next)
          next
          (try next))))
  (try first-guess))

(define (deriv g)
  (lambda (x)
    (/ (- (g (+ x dx)) (g x))
       dx)))
(define dx 0.00001)

(define (square n)(* n n))
(define (cube n)(* n n n))

(define (cubic a b c)
  (lambda (x)
    (+ (cube x)
       (* a (square x))
       (* b x)
       c)))

;; 1.41 double
;; input: procedure
;; output: procedure applied to argument twice

(define (double proc)
  (lambda (x)(proc (proc x))))

;; (((double (double double)) inc) 5) returns 21

;; 1.43  repeated
;; input: proc, a, n
;; output: proc applied to a n times

(define (repeated proc num)
  (lambda (power)(exp num power)))

(define (exp base power)
  (if (= power 0)
      1
      (* base (exp base (- power 1)))))

;; 1.46 iterative-improve
;; input: method-guess, method-improve
;; output: procedure that takes guess as arg, and keeps improving the guess until it is good enough

(define (iterative-improve good-enough method-improve)
  (define (iter guess)
    (if (good-enough guess)
        guess
        (iter (method-improve guess)))
  (iter guess)))

;;;;; #2 every
;; input: proc, arg
;; output: proc applied to every term in arg

(define (every proc arg)
  (if (empty? arg)
      arg
      (sentence (proc (first arg))(every proc (bf arg)))))

;;;;; #3
;; SO COOL!!!

