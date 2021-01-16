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

(defn generate-first-name-and-dates [first-names-set]
  (let [first-name-data (random-from-list first-names-set)
        dob (offset (:date_of_birth first-name-data))
        lifetime (:lifetime first-name-data)]
    {:first-name (:first_name first-name-data)
     :date-of-birth dob
     :date-of-death (if (not (= lifetime nil))
                      (+ dob (offset lifetime))
                      nil)}))

;; TODO: party, state of birth, last name

(defn generate-last-name [syllables-sets]
  (let [first-syllable (-> (get syllables-sets ""))]))

(defn tmp []
  (generate-first-name-and-dates (:first_names (dataset))))
