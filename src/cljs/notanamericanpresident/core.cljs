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
  (reset! state {:generating true
                 :president nil})
  (js/setTimeout (fn []
                   (reset! state
                           {:generating false :president (generate-president dataset)})) 300)
  )

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
          [:p.name (:first-name president) " " (:last-name president) " "
           (if (not (= (:date-of-death president) nil))
             [:span.date "(" (:date-of-birth president)
              " - " (:date-of-death president) ")"]

             [:span.date "(born " (:date-of-birth president) ")"])]
          [:input {:type "button" :value "Generate another president" :on-click #((stateful-generate-president))}]
          ]))]))

;; -------------------------
;; initialize app

(defn mount-root []
  (rdom/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root)
  (stateful-generate-president))

