;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen

;; #1 'new-if' versus 'if'

;; The procedure 'new-if' is not a special form, meaning it evaluates argument expressions before applying them. If we rewrite the pigl procedure using 'new-if', the result in an infinite loop because pigl is recursive. This does not happen when we use the special form 'if', since pigl isn't called again until the end (everything would've been applied by then).

;; #2 squares
;; input: sentence of numbers
;; output: sentence of squares of numbers

(define (squares s)
  (if (empty? s)
      s
      (sentence (square (first s))(squares (bf s)))))

(define (square x)
  (* x x))


;; #3 switch
;; input: sentence
;; output: sentence switchings words (i, me) with (you), (you) with (me) except at beginning where switched with (i)

(define (switch s)
  (if (equal? (first s) 'you)
      (sentence 'i (switch-k (bf s)))
      (switch-k s)))

(define (switch-k s)
  (cond ((empty? s) s)
	((equal? (first s) 'i)(sentence 'you (switch-k (bf s))))
	((equal? (first s) 'me)(sentence 'you (switch-k (bf s))))
	((equal? (first s) 'you)(sentence 'me (switch-k (bf s))))
	(else (sentence (first s)(switch-k (bf s))))))

;; 4 ordered
;; input: sentence of numbers
;; output: #t if numbers all ascending, #f otherwise

(define (ordered s)
  (cond ((empty? s) s)
	((= (count s) 1) #t)
	((< (first s)(first (bf s)))(ordered (bf s)))
	(else #f)))

;; #5 ends-e
;; input: sentence
;; output: sentence with only words ending in 'e'

(define (ends-e s)
  (cond ((empty? s) s)
	((equal? (last (first s)) 'e)(sentence (first s)(ends-e (bf s))))
	(else (ends-e (bf s)))))

;; #6

;;;;; for 'or'

;; We want to see if 'or' is a special form. So we want to check if 'or' evaluates its arguments one by one. We can do this by the following:

;; STk> (define a 2)
;; STk> (or (= a 1)(= b 2)(= c 3))
;; -> We get an error, b is undefined.
;; STk> (define a 1)
;; STk> (or (= a 1)(= b 2)(= c 3))
;; STk> #t

;; When we first do the first 'or', STk gives us an error because we did not define b. After we re-define a, STk gives us #t. This shows that 'or' is a special form, because it evaluates its argument one by one. We note that c isn't defined, but STk doesn't give us an error (it would if 'or' were an ordinary function).

;;;;; for 'and'

;; We can repeat the above process to see if 'and' is also a special form.


;; STk> (define a 2)
;; STk> (and (or (= a 1)(= b 2))(or (= c 1)(= d 2)))
;; -> We get an error, b is undefined.
;; STk> (define a 1)
;; STk> (and (or (= a 1)(= b 2))(or (= c 1)(= d 2)))
;; -> We get an error, c is undefined.
;; STk> (define c 2)
;; STk> (and (or (= a 1)(= b 2))(or (= c 1)(= d 2)))
;; -> We get an error, d is undefined.
;; STk> (define c 1)
;; STk> (and (or (= a 1)(= b 2))(or (= c 1)(= d 2)))
;; STk> #t

;; This shows that 'and' is a special form and evaluates its arguments one by one. Both b and c are not defined, but we don't get an error. However, if 'and' were a special form, we would.

;; An advantage of treating 'or' as a special form is efficiency.
;; An advantage of treating 'or' as an ordinary form is ???.
