(ns notanamericanpresident.algorithm
  (:require
   [clojure.string :as s]))

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
  (let [first-syllable (-> (:first_syllables syllables-sets)
                           (random-from-list)
                           (s/capitalize))
        last-syllable (-> (:last_syllables syllables-sets)
                          (random-from-list))
        middle-syllable? (-> (rand) (> 0.7) (if true false))
        middle-syllable (if middle-syllable?
                          (-> (:middle_syllables syllables-sets)
                              (random-from-list))
                          "")]
    (s/join [first-syllable middle-syllable last-syllable])))

(defn generate-president [dataset]
  (merge (generate-first-name-and-dates (:first_names dataset))
         (generate-last-name (:last_names dataset))))
