;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen


;; Homework 5 ;;


;; 2.24

; (list 1 (list 2 (list 3 4)))
; (1 (2 (3 4))

; [1| ]-> [ |/]
;         [2| ]-> [ |/]
;                 [3| ]-> [4|/]

;        (1 (2 (3 4))
;           / \
;          1  (2 (3 4))
;             / \
;            2   (3 4)
;                / \
;               3   4


;; 2.26

; (define x (list 1 2 3))
; (define y (list 4 5 6))

; (append x y)
; (1 2 3 4 5 6)

; (cons x y)
; ((1 2 3) 4 5 6)

; (list x y)
; ((1 2 3)(4 5 6))


;; 2.29

(define (make-mobile left right)
  (list left right))

(define (make-branch length structure)
  (list length structure))

(define (left-branch mobile)
  (car mobile))

(define (right-branch mobile)
  (cadr mobile))

(define (branch-length branch)
  (car branch))

(define (branch-structure branch)
  (cadr branch))

(define (total-weight mobile)
  (cond ((null? mobile) 0)
	((atom? mobile) 0)
	((number? (right-branch mobile))(right-branch mobile))
	(else (+ (total-weight (left-branch mobile))
		 (total-weight (right-branch mobile))))))

(define m (make-mobile (make-branch 2 (make-branch 2 5))
		       (make-branch 1 (make-branch 2 (make-branch 4 6)))))

(define k (make-mobile (make-branch 1 (make-mobile (make-branch 2 3)(make-branch 2 (make-mobile (make-branch 1 2)(make-mobile (make-branch 1 1)(make-branch 1 1))))))
		       (make-branch 1 (make-branch 1 2))))

(define l (make-mobile (make-branch 1 (make-mobile (make-branch 1 2)(make-branch 1 4)))
		       (make-branch 2 3)))

(define s (make-mobile (make-branch 1 2)
		       (make-branch 2 3)))

(define (balanced? mobile)
  (let ((bls (branch-structure (left-branch mobile)))
	(brs (branch-structure (right-branch mobile))))
    (if (number? bls)
	(if (number? brs)
	    (= (* (branch-length (left-branch mobile)) bls)
	       (* (branch-length (right-branch mobile)) brs))
	    (= (* (branch-length (left-branch mobile)) bls)
	       (* (branch-length (right-branch mobile))(total-weight brs))))
	(if (number? brs)
	    (= (* (branch-length (right-branch mobile)) brs)
	       (* (branch-length (left-branch mobile))(total-weight bls)))
	    (= (* (branch-length (left-branch mobile))(total-weight bls))
	       (* (branch-length (right-branch mobile))(total-weight brs)))))))


; If we change the represenation, then all we need to do is change the selectors.


;; 2.30

(define t (list 1 (list 2 (list 3 4) 5)(list 6 7)))

(define (square x)(* x x))

(define (square-tree tree)
  (cond ((null? tree) '())
	((atom? tree)(square tree))
	(else (cons (square-tree (datum tree))
		    (square-tree (children tree))))))

(define (square-tree-map tree)
  (map (lambda (sub-tree)
	 (if (atom? sub-tree)
	     (square sub-tree)
	     (square-tree-map sub-tree))) tree))


;; 2.31

(define (tree-map proc tree)
  (map (lambda (sub-tree)
	 (if (atom? sub-tree)
	     (proc sub-tree)
	     (tree-map proc sub-tree))) tree))

(define (tree-map-square tree)
  (tree-map square tree))


;; 2.32 subsets

(define (subsets s)
  (if (null? s)
      (list nil)
      (let ((rest (subsets (cdr s))))
        (append rest (map (lambda (x)(cons (car s) x)) rest)))))

;; rest -> (subsets '(2 3))
;;         (subsets '(3))
;; append (subsets '(2 3))(subsets '(3))(map (lambda (x)(cons 1, 2, 3, x)) (subset '(2 3), subsets '(3)))
;;
;; Subsets make delicious soup. Very, very delicious soup. Like tossing pasta and bread into a blender, we end up with a exquisite smoothie of nutricious everything. Blenders are so cool. I wish I was a blender, then I'd chop people and many things up. I would be an unstoppable blender. A vengeful blender. I don't know your name, but know this: I am coming for you, cs61a-rd. And then we can have tea and battle each other amidst a roar of wind, rain, and lightning!!!


;; 2.36

(define (accumulate-n op init seqs)
  (if (null? (car seqs))
      nil
      (cons (accumulate op init (map (lambda (x)(car x)) seqs))
            (accumulate-n op init (map (lambda (x)(cdr x)) seqs)))))

(define seq '((1 2 3) (4 5 6) (7 8 9) (10 11 12)))
(define vv '(2 2 2))
(define mm '(1 1 2))
(define id '((1 0 0)(0 1 0)(0 0 1)))


;; 2.37 matrix ops

(define (dot-product v w)
  (accumulate + 0 (map * v w)))

(define (matrix-*-vector m v)
  (map (lambda (m-vect)(dot-product m-vect v)) m))

(define (transpose m)
  (accumulate-n cons '() m))

(define (matrix-*-matrix m n)
  (let ((cols (transpose n)))
    (map (lambda (m-vect)(map (lambda (n-vect)(dot-product m-vect n-vect)) n)) m)))


;; 2.38 fold-left

(define (fold-left op initial sequence)
  (define (iter result rest)
    (if (null? rest)
        result
        (iter (op result (car rest))
              (cdr rest))))
  (iter initial sequence))

; (fold-right / 1 (list 1 2 3))
; (/ 3 2 1)
; 1.5

; (fold-left / 1 (list 1 2 3))
; (/ 1 2 3)
; 0.16666666667

; (fold-right list nil (list 1 2 3))
; (3 (2 (1 ())))

; (fold-left list nil (list 1 2 3))
; (((() 1) 2) 3)

;; The op should be commutative if fold-right and fold-left are to produce the same values for any sequence.


;; 2.54 equal?

(define (?equal? list1 list2)
  (cond ((null? list1)(null? list2))
	((null? list2)(null? list1))
	((atom? list1)(eq? list1 list2))
	((atom? list2)(eq? list2 list 1))
	((eq? (car list1)(car list2))
	 (?equal? (cdr list1)(cdr list2)))
	(else #f)))


;;;;; CALC CODE ;;;;;


;; Scheme calculator -- evaluate simple expressions


; The read-eval-print loop:


(define (calc)
  (display "calc: ")
  (flush)
  (print (calc-eval (read)))
  (calc))


; Evaluate an expression:


(define (calc-eval exp)
  (cond ((number? exp) exp)
	
	((word? exp) exp) ;;;;; INCLUDE WORDS IN CALC ;;;;;
	
	((list? exp) (calc-apply (car exp) (map calc-eval (cdr exp))))
	(else (error "Calc: bad expression:" exp))))


; Apply a function to arguments:


(define (calc-apply fn args)
  (cond ((eq? fn '+) (accumulate + 0 args))
	((eq? fn '-) (cond ((null? args) (error "Calc: no args to -"))
			   ((= (length args) 1) (- (car args)))
			   (else (- (car args) (accumulate + 0 (cdr args))))))
	((eq? fn '*) (accumulate * 1 args))
	((eq? fn '/) (cond ((null? args) (error "Calc: no args to /"))
			   ((= (length args) 1) (/ (car args)))
			   (else (/ (car args) (accumulate * 1 (cdr args))))))
	
	((eq? fn 'first)(first (car args)))
	((eq? fn 'butfirst)(butfirst (car args)))
	((eq? fn 'last)(last (car args)))
	((eq? fn 'butlast)(butlast (car args)))
	((eq? fn 'word)(accumulate word "" args))
	((eq? fn 'first-list)(every first args))
	
	((eq? fn 'garply)(every (lambda (x) x) '(garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply garply)))
	
	(else (error "Calc: bad operator:" fn))))

