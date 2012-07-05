;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen

;;;;; #1

;; 1.16 fast exp

;; input: base, number, a
;; output: base^n

(define (fast-exp b n)
  (iter-fast-exp b n 1))

(define (iter-fast-exp b counter a)
  (cond ((= counter 0) a)
	((even? counter)(iter-fast-exp (square b) (/ counter 2) a))
	(else (iter-fast-exp b (- counter 1)(* b a)))))

(define (square x)(* x x))

;; 1.35 

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

(define (phi-f x)
  (+ 1 (/ 1 x)))

;; We get that phi is approximately 1.62

;; 1.37

(define (cont-frac n d k)
  (go-go n d k 0))

(define (go-go n d k i)
  (if (> i k)
      0
      (/ (n i)(+ (d i)(go-go n d k (+ i 1))))))

(define (cont-frac-it n d k)
  (fract-it n d k (/ (n k)(d k))))

(define (fract-it n d k so-far)
  (if (= k 0)
      so-far
      (fract-it n d (- k 1)(/ (n (- k 1))(+ (d (- k 1)) so-far)))))

;; 1.38

(define (approx-e k) (cont-frac (lambda (i) 1.0) ind k))

(define (ind i)
  (cond ((= i 0) 1)
	((= i 1) 2)
	((= i 2) 1)
	((= (remainder i 3) 1)
	 (+ (ind (- i 3))(ind (- i 2))(ind (- i 1))))
	(else 1)))

;;;;; #2

(define (factors n)
  (foo n (- n 1)))

(define (foo n i)
  (if (= i 0)
      0
      (if (= (remainder n i) 0)
	  (se i (foo n (- i 1)))
	  (se (foo n (- i 1))))))

(define (sum-facts s)
  (if (empty? s)
      0
      (+ (first s)(sum-facts (bf s)))))

(define (next-perf n)
  (if (= (sum-facts (factors n)) n)
      n
      (next-perf (+ n 1))))

;;;;; #3

(define (count-change amount)
  (cc amount 5))
(define (cc amount kinds-of-coins)
  (cond ((= amount 0) 1)
        ((or (< amount 0) (= kinds-of-coins 0)) 0)
        (else (+ (cc amount
                     (- kinds-of-coins 1))
                 (cc (- amount
                        (first-denomination kinds-of-coins))
                     kinds-of-coins)))))
(define (first-denomination kinds-of-coins)
  (cond ((= kinds-of-coins 1) 1)
        ((= kinds-of-coins 2) 5)
        ((= kinds-of-coins 3) 10)
        ((= kinds-of-coins 4) 25)
        ((= kinds-of-coins 5) 50)))

(define (count-changer amount)
  (ccr amount 5))
(define (ccr amount kinds-of-coins)
  (cond ((or (< amount 0) (= kinds-of-coins 0)) 0)
        ((= amount 0) 1)
        (else (+ (ccr amount
                     (- kinds-of-coins 1))
                 (ccr (- amount
                        (first-denomination kinds-of-coins))
                     kinds-of-coins)))))

;; The outputs of the two procedures are basically the same. However, when you call (cc-modified 0 0) instead of (cc 0 0), you get 0 instead of 1. Since kinds-of-coins is 0, cc-modified returns 0. In cc, the amount is 0, so cc returns 1.

;;;;; #4

;; ???
;; base ^ n = (product * n) ^ counter
