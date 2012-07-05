;; Annie To
;; Section 17
;; cs61a-od
;; Justin Chen

;; HW 14


;; 4.56

; a)

(and (supervisor ?x (Bitdiddle Ben))
     (address ?x ?where))

; b)

(and (salary (Bitdiddle Ben) ?x)
     (and (salary ?person ?amount)
	  (lisp-value < ?amount ?x)))

; c)

(and (supervisor ?x ?head)
     (and (not (job ?head (computer . ?what)))
	  (job ?head ?other)))
     
;; 4.57

(assert!
 (rule (can-replace ?person2 ?person1)
      (and (or (and (job ?person1 ?x)
		    (job ?person2 ?x))
	       (and (job ?person1 ?y)
		    (and (job ?person2 ?z)
			 (can-do-job ?y ?z)))
	   (not (same ?person1 ?person2))))))


; a)

(can-replace (Fect Cy D) ?who)

; b)

(and (can-replace ?y ?x)
     (and (salary ?x ?a1)
	  (salary ?y ?a2)
	  (lisp-value > ?a1 ?a2)))

;; 4.58

(assert!
 (rule (big-shot ?person)
       (and (job ?person (?division . ?rest))
	    (supervisor ?person ?boss)
	    (not (job ?boss (?division . ?else))))))

;; 4.65

; That dude is listed four times because he manages four other dudes.
