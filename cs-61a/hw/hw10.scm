;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen

;;;;; Homework 7

;; 3.38

; PePaMa, PaPeMa: $45
; PeMaPa: $35
; PaMaPe: $50
; MaPePa, MaPaPe: $40


; Other values include $110, $90, $50 (set done last), $130, and MORE!
; For $130:
; Pa: $100 -> $80 | SET
; Pe: $80 + $10 = $90
; Ma: $80 -> $40 | SET
; Pe: $40 + $90 = $130 | SET

;; 3.39

; Since we have a serializer, the processes don't interweave. The possible values we can obtain are 100 and 11.

;; 3.40

; x = 10

; 1000000, 100000, 100, 1000, 10000

; If we introduce a serializer, our only possible result is 1,000,000.

;; 3.41

; No, since the balance procedure doesn't use a set! or anything. It just returns the value of balance.

;; 3.42

; This change is safe to make.

;; 3.44

; No, since we don't know the balance of both accounts, it is unnecessary to serialize the withdraw and deposit processes. For transferring, we only need to serializer other transfers accounts.

;; 3.46

; If two processes can acquire the mutex at the same time, then funny things will happen.

; P1, P2: acquires Mutex
; P1: releases Mutex
; P2: releases Mutex

; So now we have two Mutexes

;;;;;

;; 1

(define (im-list list-who message)
  (for-each (lambda (c)
	      (if (not (send-request (make-request whoiam c 'send-msg message) port-to-server))
		  (close-connection))) list-who))
