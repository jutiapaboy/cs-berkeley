;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen

;; Homework 8

;; 3.3

(define (make-account balance password)
  (define (withdraw amount)
    (if (>= balance amount)
        (begin (set! balance (- balance amount))
               balance)
        "Insufficient funds"))
  (define (deposit amount)
    (set! balance (+ balance amount))
    balance)
  (define (denied amount)
    "Incorrect password")
  (define (dispatch p m)
    (cond ((not (equal? p password)) denied)
      ((eq? m 'withdraw) withdraw)
          ((eq? m 'deposit) deposit)
          (else (error "Unknown request -- MAKE-ACCOUNT"
                       m))))
  dispatch)

;; 3.4

(define (make-account balance password rejected)
  (define (withdraw amount)
    (if (>= balance amount)
        (begin (set! balance (- balance amount))
           (set! rejected 0)
               balance)
        "Insufficient funds"))
  (define (deposit amount)
    (set! balance (+ balance amount))
    (set! rejected 0)
    balance)
  (define (denied amount)
    (set! rejected (+ rejected 1))
    "Incorrect password")
  (define (call-the-cops amount)
    "Put ur hands up in the air FOO!!!")
  (define (dispatch p m)
    (cond ((eq? rejected 7) call-the-cops)
      ((not (eq? p password)) denied)
      ((eq? m 'withdraw) withdraw)
          ((eq? m 'deposit) deposit)
          (else (error "Unknown request -- MAKE-ACCOUNT"
                       m))))
  dispatch)

;; 3.7

(define (make-account balance password)
  (define (withdraw amount)
    (if (>= balance amount)
        (begin (set! balance (- balance amount))
               balance)
        "Insufficient funds"))
  (define (deposit amount)
    (set! balance (+ balance amount))
    balance)
  (define (denied amount)
    "Incorrect password")
  (define (dispatch p m)
    (cond ((not (equal? p password)) denied)
      ((eq? m 'withdraw) withdraw)
          ((eq? m 'deposit) deposit)
          (else (error "Unknown request -- MAKE-ACCOUNT"
                       m))))
  dispatch)

(define (make-joint account account-password joint-password)
  (define (dispatch p m)
    (if (equal? p joint-password)
    (account account-password m)
    (account 'ps m)))
  dispatch)

;; 3.8

(define f
  (let ((n 1))
    (lambda (x)
      (if (= x 0)
      (begin (set! n 0) n)
      n)) ))

;; 3.10 

  (let ((balance initial-amount))
    (lambda (amount)
      (if (>= balance amount)
      (begin (set! balance (- balance amount))
         balance)
      "Insufficient funds"))))
(define (make-withdraw-expanded initial-amount)
  ((lambda (balance)
     (lambda (amount)
       (if (>= balance amount)
       (begin (set! balance (- balance amount))
          balance)
       "Insufficient funds"))) initial-amount) )
