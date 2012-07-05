;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen

;; Homework 7 ;;


;; random-generator

(define-class (random-generator num)
  (instance-vars (count 0))
  (method (number)
	  (set! count (+ count 1))
	  (random num)))


;; coke-machine

;; I think vending machines are like strippers, so I'm going to write the code for a stripping machine. I hope this doesn't bother you. (it's mainly for my own entertainment) :-)

(define-class (stripping-machine num-clothes price-per-strip)
  (instance-vars (clothes num-clothes)
		 (price price-per-strip)
		 (cash 0))
  (method (spend num)(set! cash (+ cash num))
	  cash)
  (method (strip)
	  (cond ((= clothes 0) "The machine is already naked!.")
		((< cash price) "What do you I look like to you? A cheap whore?!")
		(else (set! cash (- cash price))
		      cash)))
  (method (don num)(set! clothes (+ clothes num))
	  clothes))

;; ordered-deck

(define make-deck (map (lambda (suit)(map (lambda (card)(word card suit)) '(1 2 3 4 5 6 7 8 9 10 J K Q A))) '(D C H S)))

(define (set deck)
  (if (null? deck)
      '()
      (append (car deck)(set (cdr deck)))))

(define ordered-deck (set make-deck))
; (define shuffled-deck (shuffle ordered-deck))

(define (shuffle deck)
  (if (null? deck)
      '()
      (let ((card (nth (random (length deck)) deck)))
	    (cons card (shuffle (remove card deck))) )))

(define-class (deck)
  (instance-vars (shuffled-deck (shuffle ordered-deck)))
  (method (deal)
	  (if (null? shuffled-deck)
	      '()
	      (set! shuffled-deck (cdr shuffled-deck)))
	      shuffled-deck)
  (method (empty?)
	  (empty? shuffled-deck)))

;; miss-manners

(define-class (person name)
  (instance-vars (last-message '()))
  (method (say stuff)
	  (set! last-message stuff)
          stuff)
  (method (ask stuff)
	  (set! last-message (se '(would you please) stuff))
	  (ask self 'say (se '(would you please) stuff)))
  (method (greet)
	  (set! last-message (se '(hello my name is) name))
	  (ask self 'say (se '(hello my name is) name)))
  (method (repeat)(ask self 'last-message)))

(define-class (miss-manners OBJ)
  (method (please say arg)(ask OBJ 'say arg)))
