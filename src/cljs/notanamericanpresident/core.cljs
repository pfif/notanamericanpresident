(ns notanamericanpresident.core
  (:require
   [reagent.dom :as rdom]
   [reagent.core :as r]
   [notanamericanpresident.dataset :refer [dataset]]
   [notanamericanpresident.algorithm :refer [generate-president]]
   [clojure.string :as s]))

(def state (r/atom {:generating true
                    :president nil}))

(defn stateful-generate-president []
  (reset! state
          {:generating false :president (generate-president dataset)}))

;; -------------------------
;; Page components


(defn current-page []
  (fn []
    [:div.body
     [:h1 "American president generator" [:br] [:small "Your very own leader of the free world!"]]
     (if (:generating @state)
       [:p "generating ..."]
       (let [president (:president @state)]
         [:div.president
          [:p.name
           (:first-name president)
           " "
           (if (:middle-name president)
             [:span (:middle-name president) " "] [:span " "])

           (:last-name president) " "
           (if (not (= (:date-of-death president) nil))
             [:span.date "(" (:date-of-birth president)
              " - " (:date-of-death president) ")"]
             [:span.date "(born " (:date-of-birth president) ")"])]

          [:p.description "He hailed from the state of " [:strong (:state president)]
           " and was a member of the " [:strong (:party president)]]

          [:input {:type "button" :value "Generate another president" :on-click #(stateful-generate-president)}]]))]))

;; -------------------------
;; initialize app

(defn mount-root []
  (rdom/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (stateful-generate-president)
  (mount-root))

