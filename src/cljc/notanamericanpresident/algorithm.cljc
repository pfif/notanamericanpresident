(ns notanamericanpresident.algorithm
  (:require [notanamericanpresident.dataset :refer [dataset]]))

(defn random-from-list [set]
  (->> set
      (count)
      (rand)
      (int)
      (nth set)))

(defn offset [date]
  (let [offset (-> 20 (rand) (int))
        future-or-past (-> (rand)
                           (> 0.5)
                           (if 1 -1))]
    (+ date (* offset future-or-past)))
  )

(defn first-name-and-dates [first-names-set]
  (let [first-name-data (random-from-list first-names-set)
        dob (offset (get first-name-data "date_of_birth"))
        lifetime (get first-name-data "lifetime")]
    {:first-name (get first-name-data "first_name")
     :date-of-birth dob
     :date-of-death (if (not (= lifetime nil))
                      (+ dob (offset lifetime))
                      nil)}))

;; TODO: party, state of birth, last name

(defn tmp []
  (first-name-and-dates (-> (dataset) (get "first_names"))))
