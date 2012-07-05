;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen

;; HW 13

;; 4.25

(define (unless condition usual-value exceptional-value)
  (if condition exceptional-value usual-value))

(define (factorial n)
  (unless (= n 1)
          (* n (factorial (- n 1)))
          1))

; (if (= n 1)
;     1
;     (* n (factorial (- n 1))))

;; 4.26


(define (eval exp env)
  ;...
  ((unless? exp) (eval (unless->if exp) env))
  ;...
  )

(define (unless? exp) (tagged-list? exp 'unless))
(define (unless-clauses exp) (cdr exp))
(define (unless-condition clauses) (car clauses))
(define (unless-usual-value clauses) (cadr clauses))
(define (unless-exceptional-value clauses) (caddr clauses))
(define (unless->if exp)
  (expand-unless-clauses (unless-clauses exp)))
(define (expand-unless-clauses clauses)
  (make-if (unless-condition clauses)
           (unless-exceptional-value clauses)
           (unless-usual-value clauses)))

(define (foo f condition val1 val2)
  (f condition val1 val2))

; (foo unless (> 2 10) 1 2)

(define (unless-proc condition usual-value exceptional-value)
  (if condition exceptional-value usual-value))

; (foo unless-proc (> 2 10) 1 2)


;; 4.28

; (let ((f (lambda (x) (* x x))))
;   (f 2))

;;; 4.42 ;;;

(define (distinct? items)
   (cond ((null? items) true)
         ((null? (cdr items)) true)
         ((member (car items) (cdr items)) false)
         (else (distinct? (cdr items)))))

(define (liars)
  (let ((Betty (amb 1 2 3 4 5))
        (Ethel (amb 1 2 3 4 5))
        (Joan (amb 1 2 3 4 5))
        (Kitty (amb 1 2 3 4 5))
        (Mary (amb 1 2 3 4 5)))
    
    (require
     (distinct? (list Betty Ethel Joan Kitty Mary)))
    
    (require (or (= Kitty 2)
                 (= Betty 3)))
    (require (or (= Ethel 1)
                 (= Joan 2)))
    (require (or (= Joan 3)
                 (= Ethel 5)))
    (require (or (= Kitty 2)
                 (= Mary 4)))
    (require (or (= Mary 4)
                 (= Betty 1)))
    
    (list (list 'betty Betty)
          (list 'ethel Ethel)
          (list 'joan Joan)
          (list 'kitty Kitty)
          (list 'mary Mary))))

; (interpret (liars))
; ((betty 3) (ethel 5) (joan 2) (kitty 1) (mary 4))

;;; 4.49 ;;;

(define (get-random-element items)
  (list-ref items (random (length items))))

(define (parse-word word-list)
  (let ((found-word (get-random-element (cdr word-list))))
    (list (car word-list) found-word)))

;;; 4.50

(define (remove-from-list element items)
    (filter (lambda (x) (not (eq? x element))) items))

(define (get-random-element items)
  (list-ref items (random (length items))))

(define (ramb? exp) (tagged-list? exp 'ramb))

(define (ramb-choices exp) (cdr exp))

(define (analyze-ramb exp)
  (let ((cprocs (map analyze (ramb-choices exp))))
    (lambda (env succeed fail)
      (define (try-next choices)
        (if (null? choices)
            (fail)
            (let ((element (get-random-element choices)))
              (element env
                       succeed
                       (lambda ()
                         (try-next (remove-from-list element choices)))))))
      (try-next cprocs))))

;; Added to analyze:
; ((ramb? exp) (analyze-ramb exp))

;;; 4.52

(define (if-fail? exp)
  (tagged-list? exp 'if-fail))

(define (first-expression exp)
  (cadr exp))

(define (alternate-expression exp)
  (caddr exp))

(define (analyze-if-fail exp)
  (let ((fproc (analyze (first-expression exp)))
        (aproc (analyze (alternate-expression exp))))
    (lambda (env succeed fail)
      (fproc env
             succeed
             (lambda ()
               (aproc env succeed fail))))))

;; Addition to analyze
; ((if-fail? exp) (analyze-if-fail exp))

;; 4.33

(define (eval exp env)
  ;...
  ((quoted? exp) (eval-quoted exp env))
  ;...
  )
(define (eval-quoted exp env)
  (let ((text (text-of-quotation exp)))
    (if (pair? text)
        (eval (list 'cons (list 'quote (car text))
                    (list 'quote (cdr text)))
              env)
        text)))
