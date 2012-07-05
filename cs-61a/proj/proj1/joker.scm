;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen


;; Project 1: Twenty-One: JOKER


;;;;;;;;;; TWENTY-ONE CODE ;;;;;;;;;;


(define (twenty-one strategy)
  (define (play-dealer customer-hand dealer-hand-so-far rest-of-deck)
    (cond ((> (best-total dealer-hand-so-far) 21) 1)
	  ((< (best-total dealer-hand-so-far) 17)
	   (play-dealer customer-hand
			(se dealer-hand-so-far (first rest-of-deck))
			(bf rest-of-deck)))
	  ((< (best-total customer-hand) (best-total dealer-hand-so-far)) -1)
	  ((= (best-total customer-hand) (best-total dealer-hand-so-far)) 0)
	  (else 1)))

  (define (play-customer customer-hand-so-far dealer-up-card rest-of-deck)
    (cond ((> (best-total customer-hand-so-far) 21) -1)
	  ((strategy customer-hand-so-far dealer-up-card)
	   (play-customer (se customer-hand-so-far (first rest-of-deck))
			  dealer-up-card
			  (bf rest-of-deck)))
	  (else
	   (play-dealer customer-hand-so-far
			(se dealer-up-card (first rest-of-deck))
			(bf rest-of-deck)))))

  (let ((deck (make-deck)))
    (play-customer (se (first deck) (first (bf deck)))
		   (first (bf (bf deck)))
		   (bf (bf (bf deck))))) )

(define (make-ordered-deck)
  (define (make-suit s)
    (every (lambda (rank) (word rank s)) '(A 2 3 4 5 6 7 8 9 10 J Q K)) )
  (se 'joker 'joker (make-suit 'H) (make-suit 'S) (make-suit 'D) (make-suit 'C)) ) ;;;;; Inserting Jokers

(define (make-deck)
  (define (shuffle deck size)
    (define (move-card in out which)
      (if (= which 0)
	  (se (first in) (shuffle (se (bf in) out) (- size 1)))
	  (move-card (bf in) (se (first in) out) (- which 1)) ))
    (if (= size 0)
	deck
    	(move-card deck '() (random size)) ))
  (shuffle (make-ordered-deck) 54) ) ;;;;; We add two Jokers, so we add 2 to 52


;;;;;;;;;; TWENTY-ONE CODE END ;;;;;;;;;;


;;;;;;;;;; PROJECT CODE JOKER ;;;;;;;;;;


;;;;; #1 best-total

;; input: hand
;; output: optimized sum of hand


;;;;; !!! PREPARE FOR A MONSTER OF REDUNDANCY !!! ;;;;;


(define (best-total-joker customer-hand)
  (let ((without-anything (count customer-hand 0))
	(without-jokers (best-total customer-hand))
        (jokers (joker-count customer-hand))
	(aces (ace-count customer-hand)))
    (if (has-joker? customer-hand)
	(if (has-ace? customer-hand)
	    (count-joker-ace without-anything jokers aces)
	    (count-joker without-anything jokers))
	without-jokers)))


(define (has-joker? customer-hand)
  (if (empty? customer-hand)
      #f
      (if (equal? (last (first customer-hand)) 'r)
	  #t
	  (has-joker? (bf customer-hand)))))


;;;;; BEST-TOTAL FROM ORIGINAL CODE ;;;;;


(define (best-total customer-hand)
  (let ((without-aces (count customer-hand 0))
        (aces (ace-count customer-hand)))
    (if (has-ace? customer-hand)
	 (count-ace without-aces aces)
         without-aces)))


(define (has-ace? customer-hand)
  (if (empty? customer-hand)
      #f
      (if (equal? (first (first customer-hand)) 'a)
	  #t
	  (has-ace? (bf customer-hand)))))


(define (count customer-hand so-far)
  (cond ((empty? customer-hand) so-far)
	((member? (bl (first customer-hand)) '(j q k))(count (bf customer-hand)(+ 10 so-far)))
	((number? (bl (first customer-hand)))(count (bf customer-hand)(+ (bl (first customer-hand)) so-far)))
	(else (count (bf customer-hand) so-far))))


(define (count-ace customer-hand how-many-aces)
  (cond ((= how-many-aces 0) customer-hand)
      
        ((and (< customer-hand 11)(< (+ customer-hand 11) 21))
	 (count-ace (+ 11 customer-hand)(- how-many-aces 1)))

	((>= customer-hand 11)
	 (count-ace (+ 1 customer-hand)(- how-many-aces 1)))
	
        (else (count-ace (+ 1 customer-hand)(- how-many-aces 1)))))


(define (ace-count customer-hand)
  (if (empty? customer-hand)
      0
      (if (equal? (first (first customer-hand)) 'a)
	  (+ 1 (ace-count (bf customer-hand)))
	  (ace-count (bf customer-hand)))))


;;;;; END ;;;;;


;;;;; COUNTING JOKERS ;;;;;


(define (count-joker customer-hand how-many-jokers)
  (cond ((= how-many-jokers 0) customer-hand)

	
        ((< customer-hand 11)
	 (count-joker (+ 11 customer-hand)(- how-many-jokers 1)))

	
	((and (> customer-hand 19)(= how-many-jokers 2))
	 (count-joker (+ 2 customer-hand) 0))

	
	((> customer-hand 21)
	 (count-joker (+ 1 customer-hand)(- how-many-jokers 1)))

	
	((and (> customer-hand 10)(< (- 21 customer-hand) 11))
	 (count-joker (+ (- 21 customer-hand) customer-hand)(- how-many-jokers 1)))

	
        (else "You did something wrong. ):")))


;;;;; END ;;;;;


(define (count-joker-ace customer-hand how-many-jokers how-many-aces)
  (cond ((and (<= how-many-jokers 0)(<= how-many-aces 0))
	 customer-hand)


	((= how-many-jokers 2)
	 (count-joker (+ customer-hand how-many-aces) 2))

	((< customer-hand 16)
	 21)

	((and (= how-many-jokers 1)(= how-many-aces 1)(= customer-hand 20))
	 22)
	

	((and (>= customer-hand 16)(<= customer-hand 20))
	 (if (< (+ how-many-aces customer-hand) 22)
	     21
	     (+ customer-hand 1 how-many-aces)))
	
	((>= customer-hand 21)
	 (+ customer-hand how-many-aces 1))

	
	(else "Yo.")))
	     
	  
(define (joker-count customer-hand)
  (if (empty? customer-hand)
      0
      (if (equal? (last (first customer-hand)) 'r)
	  (+ 1 (joker-count (bf customer-hand)))
	  (joker-count (bf customer-hand)))))


;;;;; REST OF CODE FROM HERE SHOULD BE SAME AS ORIGINAL ;;;;;


;;;;; #2 stop-at-17

;; input: customer-hand, dealer-hand
;; output: #t iff total so-far is less than 17


(define (stop-at-17 customer-hand dealer-hand)
  (cond ((empty? customer-hand) #t)
	((< (best-total customer-hand) 17) #t)
	(else #f)))


;;;;; #3 play-n

;; input: strategy, n
;; output: plays n games, returns game score


(define (play-n strategy n)
 (if (= n 0)
     0
     (+ (twenty-one strategy)(play-n strategy (- n 1)))))


;;;;; #4

;; input: dealer-sensitive

;; input: c-hand, d-hand
;; output: #t iff dealer has ace, 7-10, ace, or picture card showing and c-card total less than 17
;; or #t if dealer has or 2-6 showing, and c-card total 


(define (dealer-sensitive customer-hand dealer-hand)
  (cond ((empty? customer-hand) #t)
	((and (is-member? dealer-hand '(a 7 8 9 10 j k q))
	      (< (best-total customer-hand) 17))
	 #t)
	((and (is-member? dealer-hand '(2 3 4 5 6))
	      (< (best-total customer-hand) 12))
	 #t)
	(else #f)))


(define (is-member? card-set showing-card)
  (if (empty? card-set)
      #f
      (if (member? (bl (first card-set)) showing-card)
	  #t
	  (is-member? (bf card-set) showing-card))))


;;;;; #5 stop-at

;; input: n
;; output: strategy that stops at n


(define (stop-at n)
  (lambda (customer-hand dealer-hand)
    (if (empty? customer-hand)
	#t
	(if (< (best-total customer-hand) n)
	    #t
	    #f))))

	
;;;;; #6 valentine

;; input: customer-hand, dealer-hand
;; output: strategy that stops at 17, if have heart, then stop at 19


(define (valentine customer-hand dealer-hand)
  (if (empty? customer-hand)
      #t
      (if (is-suit? customer-hand 'h)
	  ((stop-at 19) customer-hand dealer-hand)
	  ((stop-at 17) customer-hand dealer-hand))))
	

(define (is-suit? customer-hand suit)
  (if (empty? customer-hand)
      #f
      (if (member? (last (first customer-hand)) suit)
	  #t
	  (is-suit? (bf customer-hand) suit))))


;;;;; #7 suit-strategy

;; input: suit, strategy-has, stategy-else
;; output: strategy


(define (suit-strategy suit strategy-has strategy-else)
  (lambda (customer-hand dealer-hand)
    (if (is-suit? customer-hand suit)
	(strategy-has customer-hand dealer-hand)
	(strategy-else customer-hand dealer-hand))))


(define (new-valentine customer-hand dealer-hand)
  ((suit-strategy 'h (stop-at 17) (stop-at 19)) customer-hand dealer-hand))


;;;;; #8 majority

;; input: three strategies
;; output: strategy


(define (majority strategy-1 strategy-2 strategy-3)
  (lambda (customer-hand dealer-hand)
    (if (and (or (and (strategy-1 customer-hand dealer-hand)
		      (strategy-2 customer-hand dealer-hand))
		 (and (strategy-2 customer-hand dealer-hand)
		      (strategy-3 customer-hand dealer-hand))
		 (and (strategy-3 customer-hand dealer-hand)
		      (strategy-1 customer-hand dealer-hand))))
	#t
	#f)))


;;;;; #9 reckless

;; input: strategy
;; output: strategy


(define (reckless strategy)
  (lambda (customer-hand dealer-hand)
    (strategy (bl customer-hand) dealer-hand)))
