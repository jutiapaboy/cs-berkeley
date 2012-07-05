;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen


;;;;; PROJECT 2

(define (below painter1 painter2)
  (let ((split-point (make-vect 0 0.5)))
    (let ((paint-down
	   (transform-painter painter1
			      (make-vect 0 0)
			      (make-vect 1 0)
			      split-point))
	  (paint-up
	   (transform-painter painter2
			      split-point
			      (make-vect 1 0.5)
			      (make-vect 0 1))))
      (lambda (frame)
	(paint-down frame)
	(paint-up frame)))))

;; Code for CS61A project 2 -- picture language

(define (flipped-pairs painter)
  (let ((painter2 (beside painter (flip-vert painter))))
    (below painter2 painter2)))

(define (right-split painter n)
  (if (= n 0)
      painter
      (let ((smaller (right-split painter (- n 1))))
	(beside painter (below smaller smaller)))))

(define (corner-split painter n)
  (if (= n 0)
      painter
      (let ((up (up-split painter (- n 1)))
	    (right (right-split painter (- n 1))))
	(let ((top-left (beside up up))
	      (bottom-right (below right right))
	      (corner (corner-split painter (- n 1))))
	  (beside (below painter top-left)
		  (below bottom-right corner))))))

(define (square-limit painter n)
  (let ((quarter (corner-split painter n)))
    (let ((half (beside (flip-horiz quarter) quarter)))
      (below (flip-vert half) half))))

(define (square-of-four tl tr bl br)
  (lambda (painter)
    (let ((top (beside (tl painter) (tr painter)))
	  (bottom (beside (bl painter) (br painter))))
      (below bottom top))))

(define (identity x) x)

(define (flipped-pairs painter)
  (let ((combine4 (square-of-four identity flip-vert
				  identity flip-vert)))
    (combine4 painter)))

;; or

; (define flipped-pairs
;   (square-of-four identity flip-vert identity flip-vert))

(define (square-limit painter n)
  (let ((combine4 (square-of-four flip-horiz identity
				  rotate180 flip-vert)))
    (combine4 (corner-split painter n))))

(define (frame-coord-map frame)
  (lambda (v)
    (add-vect
     (origin-frame frame)
     (add-vect (scale-vect (xcor-vect v)
			   (edge1-frame frame))
	       (scale-vect (ycor-vect v)
			   (edge2-frame frame))))))

(define (segments->painter segment-list)
  (lambda (frame)
    (for-each
     (lambda (segment)
       (draw-line
	((frame-coord-map frame) (start-segment segment))
	((frame-coord-map frame) (end-segment segment))))
     segment-list)))

(define (draw-line v1 v2)
  (penup)
  (setxy (- (* (xcor-vect v1) 200) 100)
	 (- (* (ycor-vect v1) 200) 100))
  (pendown)
  (setxy (- (* (xcor-vect v2) 200) 100)
	 (- (* (ycor-vect v2) 200) 100)))

(define (transform-painter painter origin corner1 corner2)
  (lambda (frame)
    (let ((m (frame-coord-map frame)))
      (let ((new-origin (m origin)))
	(painter
	 (make-frame new-origin
		     (sub-vect (m corner1) new-origin)
		     (sub-vect (m corner2) new-origin)))))))

(define (flip-vert painter)
  (transform-painter painter
		     (make-vect 0.0 1.0)
		     (make-vect 1.0 1.0)
		     (make-vect 0.0 0.0)))

(define (shrink-to-upper-right painter)
  (transform-painter painter
		    (make-vect 0.5 0.5)
		    (make-vect 1.0 0.5)
		    (make-vect 0.5 1.0)))

(define (rotate90 painter)
  (transform-painter painter
		     (make-vect 1.0 0.0)
		     (make-vect 1.0 1.0)
		     (make-vect 0.0 0.0)))

(define (squash-inwards painter)
  (transform-painter painter
		     (make-vect 0.0 0.0)
		     (make-vect 0.65 0.35)
		     (make-vect 0.35 0.65)))

(define (beside painter1 painter2)
  (let ((split-point (make-vect 0.5 0.0)))
    (let ((paint-left
	   (transform-painter painter1
			      (make-vect 0.0 0.0)
			      split-point
			      (make-vect 0.0 1.0)))
	  (paint-right
	   (transform-painter painter2
			      split-point
			      (make-vect 1.0 0.0)
			      (make-vect 0.5 1.0))))
      (lambda (frame)
	(paint-left frame)
	(paint-right frame)))))

(define (make-frame origin edge1 edge2)
  (list origin edge1 edge2))

(define (make-frame origin edge1 edge2)
  (cons origin (cons edge1 edge2)))

;;
;; Your code goes here
;;


;;;;; MY CODE GOES HERE ;;;;;


;; 2.44 up-split


(define (up-split painter n)
  (if (= n 0)
      painter
      (let ((smaller (up-split painter (- n 1))))
	(below painter (beside smaller smaller)))))


;; 2.45 split


(define (split where-id where-sections)
  (lambda (painter n)
    (if (= n 0)
	painter
	(let ((smaller ((split where-id where-sections) painter (- n 1))))
	  (where-id painter (where-sections smaller smaller))))))


(define split-right (split beside below))
(define split-up-split (split below beside))


;; 2.46 make-vect, xcor-vect, ycor-vect, add-vect, sub-vect, scale-vect


(define (make-vect x y)
  (cons x y))


(define (xcor-vect vect)
  (car vect))


(define (ycor-vect vect)
  (cdr vect))


;;;;; Vector Arithmetic ;;;;;


(define (add-vect v1 v2)
  (make-vect (+ (xcor-vect v1)
		(xcor-vect v2))
	     (+ (ycor-vect v1)
		(ycor-vect v2))))


(define (sub-vect v1 v2)
  (make-vect (- (xcor-vect v1)
		(xcor-vect v2))
	     (- (ycor-vect v1)
		(ycor-vect v2))))


(define (scale-vect c vect)
  (make-vect (* c (xcor-vect vect))
	     (* c (ycor-vect vect))))


;; 2.47 selectors for frames


(define (origin-frame frame)
  (car frame))


(define (edge1-frame frame)
  (car (cdr frame)))


(define (edge2-frame frame)
  (cdr (cdr frame)))


;; 2.48 make-segment, start-segment, end-segment


(define (make-segment v1 v2)
  (cons v1 v2))


(define (start-segment segment)
  (car segment))


(define (end-segment segment)
  (cdr segment))


;; 2.49 use segments->painter to define painters


(define (outline frame)
  ((segments->painter (list
		       
 		       (make-segment (make-vect 0 0)(make-vect 0 1))
 		       (make-segment (make-vect 0 1)(make-vect 1 1))
		       (make-segment (make-vect 1 1)(make-vect 1 0))
		       (make-segment (make-vect 1 0)(make-vect 0 0))))
   frame))


(define (x-frame frame)
  ((segments->painter (list
		       
		       (make-segment (make-vect 1 0)(make-vect 0 1))
		       (make-segment (make-vect 1 1)(make-vect 0 0))))			     
   frame))


(define (diamond-frame frame)
  (let ((e1 (make-vect 1 0))
	(e2 (make-vect 0 1)))
    ((segments->painter (list
			 
			 (make-segment (scale-vect 0.5 e1)(scale-vect 0.5 e2))
			 (make-segment (scale-vect 0.5 e2)(add-vect (scale-vect 0.5 e1) e2))
			 (make-segment (add-vect (scale-vect 0.5 e1) e2)(add-vect e1 (scale-vect 0.5 e2)))
			 (make-segment (add-vect e1 (scale-vect 0.5 e2))(scale-vect 0.5 e1))))
     frame)))
 

;_; Colloborated With FRIENDS ;_; Because WAVE GUY SUCKS! ;_;


(define (wave frame)
  (let ((e1 (scale-vect (/ 1 16) (make-vect 1 0)))
        (e2 (scale-vect (/ 1 16) (make-vect 0 1))))
    ((segments->painter (list

                         ; This is the code for a moustache ;
			 
			 (make-segment (make-vect 0.5 0.75)(make-vect 0.55 0.78))
			 (make-segment (make-vect 0.55 0.78)(make-vect 0.60 0.73))
			 (make-segment (make-vect 0.60 0.73)(make-vect 0.53 0.72))
			 (make-segment (make-vect 0.5 0.75)(make-vect 0.53 0.72))

			 (make-segment (make-vect 0.45 0.78)(make-vect 0.5 0.75))
			 (make-segment (make-vect 0.40 0.73)(make-vect 0.45 0.78))
			 (make-segment (make-vect 0.40 0.73)(make-vect 0.47 0.72))
			 (make-segment (make-vect 0.47 0.72)(make-vect 0.5 0.75))
		       
			 ; End ;
			 
			 (make-segment (add-vect (scale-vect 0 e1) (scale-vect 10 e2))
				       (add-vect (scale-vect 3 e1) (scale-vect 6 e2)))
                         (make-segment (add-vect (scale-vect 3 e1) (scale-vect 6 e2))
                                       (add-vect (scale-vect 5 e1) (scale-vect 9 e2)))
                         (make-segment (add-vect (scale-vect 5 e1) (scale-vect 9 e2))
                                       (add-vect (scale-vect 6 e1) (scale-vect 7 e2)))
                         (make-segment (add-vect (scale-vect 6 e1) (scale-vect 7 e2))
                                       (add-vect (scale-vect 4 e1) (scale-vect 0 e2)))
                         (make-segment (add-vect (scale-vect 6.5 e1) (scale-vect 0 e2))
                                       (add-vect (scale-vect 8 e1) (scale-vect 5 e2)))
                         (make-segment (add-vect (scale-vect 8 e1) (scale-vect 5 e2))
                                       (add-vect (scale-vect 9.5 e1) (scale-vect 0 e2)))
                         (make-segment (add-vect (scale-vect 12 e1) (scale-vect 0 e2))
                                       (add-vect (scale-vect 9.5 e1) (scale-vect 6.5 e2)))
                         (make-segment (add-vect (scale-vect 9.5 e1) (scale-vect 6.5 e2))
                                       (add-vect (scale-vect 16 e1) (scale-vect 3 e2)))
                         (make-segment (add-vect (scale-vect 16 e1) (scale-vect 6 e2))
                                       (add-vect (scale-vect 12 e1) (scale-vect 10 e2)))
                         (make-segment (add-vect (scale-vect 12 e1) (scale-vect 10 e2))
                                       (add-vect (scale-vect 9.5 e1) (scale-vect 10 e2)))
                         (make-segment (add-vect (scale-vect 9.5 e1) (scale-vect 10 e2))
                                       (add-vect (scale-vect 10.25 e1) (scale-vect 13 e2)))
                         (make-segment (add-vect (scale-vect 10.25 e1) (scale-vect 13 e2))
                                       (add-vect (scale-vect 9.5 e1) (scale-vect 16 e2)))
                         (make-segment (add-vect (scale-vect 6.5 e1) (scale-vect 16 e2))
                                       (add-vect (scale-vect 5.75 e1) (scale-vect 13 e2)))
                         (make-segment (add-vect (scale-vect 5.75 e1) (scale-vect 13 e2))
                                       (add-vect (scale-vect 6.5 e1) (scale-vect 10 e2)))
                         (make-segment (add-vect (scale-vect 6.5 e1) (scale-vect 10 e2))
                                       (add-vect (scale-vect 5 e1) (scale-vect 10 e2)))
                         (make-segment (add-vect (scale-vect 5 e1) (scale-vect 10 e2))
                                       (add-vect (scale-vect 3 e1) (scale-vect 9 e2)))
                         (make-segment (add-vect (scale-vect 3 e1) (scale-vect 9 e2))
                                       (add-vect (scale-vect 0 e1) (scale-vect 13 e2)))))
     frame)))


;; 2.50 flip-horiz, rotate180, rotate270 (cc)


(define (flip-horiz painter)
  (transform-painter painter
		     (make-vect 1 0)
		     (make-vect 0 0)
		     (make-vect 1 1)))


(define (rotate180 painter)
  (transform-painter painter
		     (make-vect 1 1)
		     (make-vect 0 1)
		     (make-vect 1 0)))


(define (rotate270 painter)
  (transform-painter painter
		     (make-vect 0 1)
		     (make-vect 0 0)
		     (make-vect 1 1)))

;; 2.51 below


(define (below painter1 painter2)
  (let ((split-point (make-vect 0 0.5)))
    (let ((paint-down
	   (transform-painter painter1
			      (make-vect 0 0)
			      (make-vect 1 0)
			      split-point))
	  (paint-up
	   (transform-painter painter2
			      split-point
			      (make-vect 1 0.5)
			      (make-vect 0 1))))
      (lambda (frame)
	(paint-down frame)
	(paint-up frame)))))


(define (below2 painter1 painter2)
  (rotate270 (beside (rotate90 painter1)
	             (rotate90 painter2))))

		     
;; 2.52 change wave, corner-split, square-limit


(define (corner-split2 painter n)
  (if (= n 0)
      painter
      (let ((up (up-split painter (- n 1)))
	    (right (right-split painter (- n 1)))
	    (corner (corner-split2 painter (- n 1))))
	  (beside (below painter up)
		  (below right corner)))))


(define (square-limit2 painter n)
  ((square-of-four flip-horiz flip-vert rotate90 rotate270)(corner-split painter n)))


;;;;; THE FRAME ;;;;;


(define full-frame (make-frame (make-vect -0.5 -0.5)
			       (make-vect 2 0)
			       (make-vect 0 2)))
