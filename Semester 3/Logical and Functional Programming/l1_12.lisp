;here we solve problem 12 from l1
;point a

;(declare (function dot-product (list list)))

; mathematical model
; dot_product(a:l1...ln, b:k1...km)=
;	0, len a = 0 or len b = 0
;	a1 * b1 + dot_product(l2...ln, k2...km), otherwise

(defun dot_product (a b)
  (if (or(null a)(null b))
      0
      (+ (* (car a) (car b)) (dot_product (cdr a) (cdr b)))
      )
  )


;(declare (function main_a ()))


(defun main_a ()
  (let ((v1 '(1 2 3))
        (v2 '(1 2 3)))
    (print (dot_product v1 v2))
    )
  )

;point b

;(declare (function maximum_in_list (sequence)))

; mathematical model
; maximum_in_list(l:l1...ln)
;	l1, if l1 is a number
;	-1, if l1 is not a list
;	max(maximum_in_list(l2...ln)), otherwise

(defun maximum_in_list (l)
  (cond
    ((numberp l) l)
    ((atom l) -1)
    (T (apply 'max (mapcar 'maximum_in_list l)))
    )
  )

;(declare (function main_b ()))

(defun main_b ()
  (let ((l '(1 2 3 (4 20 2) 5 (12))))
    (print (maximum_in_list l))
    )
  )

;point c
;(declare (function operation_solve (character number number)))

; mathematical model
; operation_solve(o:atom a:number b:number)
;	a+b, if o="+"
;	a-b, if o="-"
;	a*b, if o="*"
;	a/b, if o="/"

(defun operation_solve (o a b)
  (cond
    ((string= o "+") (+ a b))
    ((string= o "-") (- a b))
    ((string= o "*") (* a b))
    ((string= o "/") (floor a b)) 
   )
  )

;(declare (function expression_solve (or number sequence)))

; mathematical model
; expression(l:l1..ln)
;	nil, if n = 0
;	operation(l1 l2 l3) U expression(l4...ln), if l1 is an operator, l2 l3 numbers
;	l1 U expression(l2..ln), otherwise

(defun expression (l)
  (cond
    ((null l) nil)
    ((and (and (not (numberp (car l))) (numberp (cadr l))) (numberp (caddr l)))
     (cons (operation_solve (car l) (cadr l) (caddr l)) (expression (cdddr l))))
    (t (cons (car l) (expression (cdr l))))
    )
  )

;(declare (function solve (sequence)))

(defun solve (l)
  (cond
    ((null (cdr l)) (car l))
    (t (solve (expression l)))
    )
  )

;(declare (function main_c ()))

(defun main_c ()
  (let
      ((v1 '(+ 1 3))
       (v2 '(+ * 2 4 3))
       (v3 '(+ * 2 4 - 5 * 2 2)))
    (print (solve v1))
    (print (solve v2))
    (print (solve v3)))
  )

;point d

;(declare (function even_length (or number list)))

; mathematical model
; even_length(l:l1..ln)
;	true, if n = 0 or
;	false, if l2...ln is null
; even_length(l3...ln), otherwise


(defun even_length (l)
  (if (null l)
      t
      (if(null (cdr l))
         nil
         (even_length(cddr l))
         )
      )
  )


;(declare (function main_d ()))

(defun main_d ()
  (let ((v1 '(1 2 2 4))
        (v2 '(1 2 4 2 4)))
    (print (even_length v1))
    (print (even_length v2)))
  )
