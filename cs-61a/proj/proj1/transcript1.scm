;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen


;; Project 1: Twenty-One: TRANSCRIPT


;;;;; #1 best-total


STk> (best-total '(aa aa aa))
.. -> best-total with customer-hand = (aa aa aa)
.. <- best-total returns 13


13
STk> (best-total '(1a 2s jk 10k aa))
.. -> best-total with customer-hand = (1a 2s jk 10k aa)
.. <- best-total returns 24


24
STk> (best-total '(aa 2j 3k))
.. -> best-total with customer-hand = (aa 2j 3k)
.. <- best-total returns 16
16


STk> (best-total '(aa jk aa jm aa))
.. -> best-total with customer-hand = (aa jk aa jm aa)
.. <- best-total returns 23
23


;;;;; #2 stop-at-17


STk> (stop-at-17 '(10k 3d) '10d)
.. -> stop-at-17 with customer-hand = (10k 3d),  dealer-hand = 10d
.... -> best-total with customer-hand = (10k 3d)
.... <- best-total returns 13
.. <- stop-at-17 returns #t
#t


STk> (stop-at-17 '(10k 9d) '10d)
.. -> stop-at-17 with customer-hand = (10k 9d),  dealer-hand = 10d
.... -> best-total with customer-hand = (10k 9d)
.... <- best-total returns 19
.. <- stop-at-17 returns #f
#f


;;;;; #3 play-n


STk> (play-n valentine 2)
.. -> play-n with strategy = #[closure arglist=(customer-hand dealer-hand) 261e38],  n = 2
.... -> best-total with customer-hand = ("6d" "9c")
.... <- best-total returns 15
.... -> best-total with customer-hand = ("6d" "9c")
.... <- best-total returns 15
.... -> best-total with customer-hand = ("6d" "9c" "9h")
.... <- best-total returns 24
.... -> play-n with strategy = #[closure arglist=(customer-hand dealer-hand) 261e38],  n = 1
...... -> best-total with customer-hand = ("7h" "5h")
...... <- best-total returns 12
...... -> best-total with customer-hand = ("7h" "5h")
...... <- best-total returns 12
...... -> best-total with customer-hand = ("7h" "5h" qs)
...... <- best-total returns 22
...... -> play-n with strategy = #[closure arglist=(customer-hand dealer-hand) 261e38],  n = 0
...... <- play-n returns 0
.... <- play-n returns -1
.. <- play-n returns -2
-2


STk> (play-n (stop-at 17) 2)
.. -> play-n with strategy = #[closure arglist=(customer-hand dealer-hand) 19e590],  n = 2
.... -> best-total with customer-hand = ("2s" kc)
.... <- best-total returns 12
.... -> best-total with customer-hand = ("2s" kc)
.... <- best-total returns 12
.... -> best-total with customer-hand = ("2s" kc "6c")
.... <- best-total returns 18
.... -> best-total with customer-hand = ("2s" kc "6c")
.... <- best-total returns 18
.... -> best-total with customer-hand = (qd qs)
.... <- best-total returns 20
.... -> best-total with customer-hand = (qd qs)
.... <- best-total returns 20
.... -> best-total with customer-hand = ("2s" kc "6c")
.... <- best-total returns 18
.... -> best-total with customer-hand = (qd qs)
.... <- best-total returns 20
.... -> play-n with strategy = #[closure arglist=(customer-hand dealer-hand) 19e590],  n = 1
...... -> best-total with customer-hand = (kh "3d")
...... <- best-total returns 13
...... -> best-total with customer-hand = (kh "3d")
...... <- best-total returns 13
...... -> best-total with customer-hand = (kh "3d" "7c")
...... <- best-total returns 20
...... -> best-total with customer-hand = (kh "3d" "7c")
...... <- best-total returns 20
...... -> best-total with customer-hand = ("6d" "8c")
...... <- best-total returns 14
...... -> best-total with customer-hand = ("6d" "8c")
...... <- best-total returns 14
...... -> best-total with customer-hand = ("6d" "8c" jh)
...... <- best-total returns 24
...... -> play-n with strategy = #[closure arglist=(customer-hand dealer-hand) 19e590],  n = 0
...... <- play-n returns 0
.... <- play-n returns 1
.. <- play-n returns 0
0


;;;;; #4 dealer-sensitive


;; Dealer has Ace total and total is less than 17 ;;


STk> (dealer-sensitive '(1s 2d) '(ad))
.. -> dealer-sensitive with customer-hand = (1s 2d),  dealer-hand = (ad)
.... -> best-total with customer-hand = (1s 2d)
.... <- best-total returns 3
.. <- dealer-sensitive returns #t
#t


;; Dealer has ace, but total is over 17 ;;


STk> (dealer-sensitive '(10s 8d) '(ad))
.. -> dealer-sensitive with customer-hand = (10s 8d),  dealer-hand = (ad)
.... -> best-total with customer-hand = (10s 8d)
.... <- best-total returns 18
.. <- dealer-sensitive returns #f
#f


;; Dealer has 2, but total is over 12 ;;


STk> (dealer-sensitive '(10s 8d) '(2d))
.. -> dealer-sensitive with customer-hand = (10s 8d),  dealer-hand = (2d)
.... -> best-total with customer-hand = (10s 8d)
.... <- best-total returns 18
.. <- dealer-sensitive returns #f
#f

;; Dealer has 2 and total is under 12 ;;


STk> (dealer-sensitive '(4s 2d) '(2d))
.. -> dealer-sensitive with customer-hand = (4s 2d),  dealer-hand = (2d)
.... -> best-total with customer-hand = (4s 2d)
.... <- best-total returns 6
.. <- dealer-sensitive returns #t
#t


;;;;; #5 stop-at


STk> (stop-at 3)
#[closure arglist=(customer-hand dealer-hand) 17afb0]
STk> ((stop-at 3) '(1a) '(3d))
.. -> best-total with customer-hand = (1a)
.. <- best-total returns 1
#t


STk> ((stop-at 3) '(5a) '(3d))
.. -> best-total with customer-hand = (5a)
.. <- best-total returns 5
#f


;;;;; #6 valentine


STk> (valentine '(10k 3k) '(3d 1a))
.. -> valentine with customer-hand = (10k 3k),  dealer-hand = (3d 1a)
.... -> best-total with customer-hand = (10k 3k)
.... <- best-total returns 13
.. <- valentine returns #t
#t


STk> (valentine '(10k 8k) '(3d 1a))
.. -> valentine with customer-hand = (10k 8k),  dealer-hand = (3d 1a)
.... -> best-total with customer-hand = (10k 8k)
.... <- best-total returns 18
.. <- valentine returns #f
#f


STk> (valentine '(10k 8h) '(3d 1a))
.. -> valentine with customer-hand = (10k 8h),  dealer-hand = (3d 1a)
.... -> best-total with customer-hand = (10k 8h)
.... <- best-total returns 18
.. <- valentine returns #t
#t


;;;;; #7 suit-strategy


STk> (suit-strategy 'h (stop-at 19) (stop-at 17))
#[closure arglist=(customer-hand dealer-hand) 2260f8]
STk> ((suit-strategy 'h (stop-at 19) (stop-at 17)) '(10d 3d) '(1a 2s))
.. -> best-total with customer-hand = (10d 3d)
.. <- best-total returns 13
#t


STk> ((suit-strategy 'h (stop-at 19) (stop-at 17)) '(10d 8d) '(1a 2s))
.. -> best-total with customer-hand = (10d 8d)
.. <- best-total returns 18
#f


STk> ((suit-strategy 'h (stop-at 19) (stop-at 17)) '(10d 8h) '(1a 2s))
.. -> best-total with customer-hand = (10d 8h)
.. <- best-total returns 18
#t


;;;;; #8 majority


STk> ((majority stop-at-17 (stop-at 2) valentine) '(10a 8d) '(3d))
.. -> majority with strategy-1 = #[closure arglist=l 2794e8],  strategy-2 = #[closure arglist=(customer-hand dealer-hand) 1750c0],  strategy-3 = #[closure arglist=l 27aeb8]
.. <- majority returns #[closure arglist=(customer-hand dealer-hand) 2863b8]
.. -> stop-at-17 with customer-hand = (10a 8d),  dealer-hand = (3d)
.... -> best-total with customer-hand = (10a 8d)
.... <- best-total returns 18
.. <- stop-at-17 returns #f
.. -> best-total with customer-hand = (10a 8d)
.. <- best-total returns 18
.. -> valentine with customer-hand = (10a 8d),  dealer-hand = (3d)
.... -> best-total with customer-hand = (10a 8d)
.... <- best-total returns 18
.. <- valentine returns #f
#f


STk> ((majority stop-at-17 (stop-at 2) valentine) '(10a 8h) '(3d))
.. -> majority with strategy-1 = #[closure arglist=l 2794e8],  strategy-2 = #[closure arglist=(customer-hand dealer-hand) 25e048],  strategy-3 = #[closure arglist=l 27aeb8]
.. <- majority returns #[closure arglist=(customer-hand dealer-hand) 25d118]
.. -> stop-at-17 with customer-hand = (10a 8h),  dealer-hand = (3d)
.... -> best-total with customer-hand = (10a 8h)
.... <- best-total returns 18
.. <- stop-at-17 returns #f
.. -> best-total with customer-hand = (10a 8h)
.. <- best-total returns 18
.. -> valentine with customer-hand = (10a 8h),  dealer-hand = (3d)
.... -> best-total with customer-hand = (10a 8h)
.... <- best-total returns 18
.. <- valentine returns #t
.. -> stop-at-17 with customer-hand = (10a 8h),  dealer-hand = (3d)
.... -> best-total with customer-hand = (10a 8h)
.... <- best-total returns 18
.. <- stop-at-17 returns #f
#f


;;;;; #9 reckless


STk> ((reckless valentine) '(10j 8h 3d) '(3d))
.. -> reckless with strategy = #[closure arglist=l 27aeb8]
.. <- reckless returns #[closure arglist=(customer-hand dealer-hand) 26e698]
.. -> valentine with customer-hand = (10j 8h),  dealer-hand = (3d)
.... -> best-total with customer-hand = (10j 8h)
.... <- best-total returns 18
.. <- valentine returns #t
#t


STk> ((reckless valentine) '(10j 10h 8d) '(3d))
.. -> reckless with strategy = #[closure arglist=l 27aeb8]
.. <- reckless returns #[closure arglist=(customer-hand dealer-hand) 2522b8]
.. -> valentine with customer-hand = (10j 10h),  dealer-hand = (3d)
.... -> best-total with customer-hand = (10j 10h)
.... <- best-total returns 20
.. <- valentine returns #f
#f


;;;;;;;;;; JOKER;;;;;;;;;


STk> (best-total-joker '(aa aa joker joker))
.. -> best-total-joker with customer-hand = (aa aa joker joker)
.. <- best-total-joker returns 21
21


STk> (best-total-joker '(aa joker joker aa 10k))
.. -> best-total-joker with customer-hand = (aa joker joker aa 10k)
.. <- best-total-joker returns 21
21


STk> (best-total-joker '(10k 1s 3d jk joker aa))
.. -> best-total-joker with customer-hand = (10k 1s 3d jk joker aa)
.. <- best-total-joker returns 26
26


STk> (best-total-joker '(joker aa aa aa aa))
.. -> best-total-joker with customer-hand = (joker aa aa aa aa)
.. <- best-total-joker returns 21
21


STk> (best-total-joker '(joker joker aa aa aa aa))
.. -> best-total-joker with customer-hand = (joker joker aa aa aa aa)
.. <- best-total-joker returns 21
21


STk> (best-total-joker '(10k jk aa joker joker))
.. -> best-total-joker with customer-hand = (10k jk aa joker joker)
.. <- best-total-joker returns 23
23


STk> (best-total-joker '(aa joker))
.. -> best-total-joker with customer-hand = (aa joker)
.. <- best-total-joker returns 21
21
