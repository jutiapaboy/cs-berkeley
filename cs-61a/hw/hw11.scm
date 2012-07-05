;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen

;; HW 11

;; 4.3


(define (mc-eval exp env)
  (cond ((self-evaluating? exp) exp)
	((variable? exp) (lookup-variable-value exp env))
	((get 'p (car exp))
	 ((get 'p (car exp)) exp env))
	((application? exp)
	 (mc-apply (mc-eval (operator exp) env)
		   (list-of-values (operands exp) env)))
	(else
	 (error "Unknown expression type -- EVAL" exp))))

(put 'p 'quoted? (lambda (exp env)(text-of-quotation exp)))
(put 'p 'assigmnent? (lambda (exp env)(eval-assignment exp env)))
(put 'p 'definition? (lambda (exp env)(eval-definition exp env)))
(put 'p 'if-exp (lambda (exp env)(eval-if exp env)))
(put 'p 'lambda? (lambda (exp env)
		   (make-procedure (lambda-parameters exp)
				   (lambda-body exp)
				   env))))
; ETC

;; 4.6

(define (let->combination exp)
  (let ((vars-vals (cadr exp))
	(body (caddr exp)))
    (let ((parameters (map car vars-vals))
	  (exps (map cdr vars-vals)))
      (cons (make-lambda parameters body) exps))))

(define (let? exp)
  (tagged-lis? exp 'let))

;; 4.13

; Remove the binding in the first frame

(define (make-unbound! var env)
  (let ((frame1 (car env)))
    (define (scan vars vals)
      (cond ((null? vars)
	     (error ""))
	    ((eq? var (car vars))
	     (set-car! vars '())
	     (set-car! vals '()))
	    (else (scan (cdr vars)(cdr vals))))))
  (scan (car (car frame1))
	(cdr (car frame1))))

;; 4.14

; The arguments to STk's map and mceval's map are written differently. Since STk's map expects a normal argument, we get an error when we try to feed it an argument written for mceval.

;; 4.15

; When we do (try try), we evaluate (halts? try try). If it returns true, then (try try) is supposed to halt. But instead we get (run-forever). If (try try) is false, then that means (try try) runs forever, but instead it halts.

;; 4.22

; We add in ((let? exp)(let->combination exp)) in the evaluator.

;; 4.24

