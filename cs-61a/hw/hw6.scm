;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen

;; Homework 6 ;;

;; 2.74

(define name car)
(define content cdr)

(define (get-record record)
  (name record))

(define (get-salary record)
  (get (get-record record)(content record) record))

(define (find-employee-record name list)
  (get 'name '(content list) list))

;; The only changes that need to be made is adding the new personnel information, and making sure the order of the records are congruent.


;; 2.75

(define (make-from-mag-ang mag ang)
  (define (dispatch op)
    (cond ((eq? op 'mag-part) mag)
	  ((eq? op 'ang-part) ang)
	  ((eq? op 'x-part)
	   (* m (cos ang)))
	  ((eq? op 'y-part)
	   (* m (sin ang)))
	  (else "Hay")))
  dispatch)


;; 2.76

;; Generic operation: everything needs to be written
;; Data-direction: new operations need to be added
;; Message-directed: new types need to be added

;; If changes often occur, message-passing style would be more appropriate, since it does not discriminate against age, sex, or race. If new operations are often added, data-directed would be more appropriate


;; 2.77

;; It works because it now has selectors.


;; 2.79

(define (equ x y) (apply-generic 'equ x y))

(put 'equ '(scheme-number scheme-number)
     (lambda (x y)(= x y)))

(define (equ-rat x y) ;; equ-rat
    (= (/ (numer x)(denom x))
       (/ (numer y)(denom y))))

(put 'equ '(rational rational)
     (lambda (x y)(tag (equ-rat x y))))

(define (equ-complex z1 z2)
    (and (= (real-part z1) (real-part z2))
	 (= (imag-part z1) (imag-part z2))))

(put 'equ '(complex complex)
     (lambda (z1 z2)(tag (equ-complex z1 z2))))


;; 2.80

(define (=zero? x y)(apply-generic 'equ x y))

(put '=zero? '(scheme-number scheme-number)
     (lambda (x)(= x 0)))

(define (=zero?-rat x)
  (= (/ (numer x)(denom x)) 0))

(put '=zero? '(rational rational)
     (lambda (x)(tag (=zero?-rat x))))

(define (=zero?-complex z1)
  (and (= (real-part z) 0)
       (= (imag-part z) 0)))

(put '=zero? '(complex complex)
     (lambda (z1)(tag (=zero?0complex z1))))

;; 2.81

;; a) If two complex numbers are used as arguments, we get an infinite loop because apply-generic doesn't have a base case for that situation.

;; b) Yes

;; c) ???


;; 2.83

(define (raise x)(apply-generic 'raise x))

(put 'raise (scheme-number scheme-number)
     (lambda (x)(make-rat x 1)))

(define (raise-rat x)
  (make-real (/ (numer x)(denom x))))

(define (raise-real x)
  (make-from-real-imag x 0))

