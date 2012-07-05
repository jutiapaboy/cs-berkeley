;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen

;; HW 17

; 3.50

(define (stream-map2 proc . argstreams)
  (if (stream-null? (car argstreams))
    the-empty-stream
    (cons-stream
     (apply proc (map stream-car argstreams))
     (apply stream-map2 (cons proc (map stream-cdr argstreams))))))

; 3.51

; (stream-ref x 5)
;
;1
;2
;3
;4
;5

; (stream-ref x 7)

;6
;7

; =/

; 3.52

(define sum 0)

(define (accum x)
  (set! sum (+ x sum))
  sum)

; 3.53

; (1 2 4 8 16 ... 2^n)

; 3.54

(define (add-streams s1 s2)
  (stream-map (lambda (a b) (+ a b) s1 s2)))

(define (mul-streams s1 s2)
  (stream-map (lambda (a b) (* a b)) s1 s2))

(define ints (cons-stream 1 (stream-map (lambda (x) (+ 1 x)) ints)))

(define factorials (cons-stream 1 (mul-streams factorials ints)))

; 3.55

(define (partial-sums s)
  (define (helper s-in last)
    (let ((cur (+ (car s-in) last)))
      (cons-stream cur (helper (stream-cdr s-in) cur))))
  (helper s 0))

; 3.64

(define (stream-limit s tolerance)
  (if (< (abs (- (stream-car s)
(stream-car (stream-cdr s))))
tolerance)
      (stream-car (stream-cdr s))
      (stream-limit (stream-cdr s) tolerance)))

; 3.66

;; ): 

				

