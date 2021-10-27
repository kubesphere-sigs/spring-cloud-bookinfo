package main

import (
	"encoding/json"
	"github.com/gorilla/mux"
	"log"
	"net/http"
	"strconv"
)

func main() {
	r := mux.NewRouter()
	r.HandleFunc("/api/v1/reviews/{id:[0-9]+}/ratings", handler).Methods("GET")
	log.Println("Server listen on 8080")
	log.Fatal(http.ListenAndServe(":8080", r))
}

func handler(w http.ResponseWriter, r *http.Request) {
	reviewID, _ := strconv.Atoi(mux.Vars(r)["id"])
	var result *Rating
	for _, rating := range RatingList {
		if rating.ReviewID == reviewID {
			result = &rating
			break
		}
	}
	if result == nil {
		w.WriteHeader(404)
		w.Write([]byte(http.StatusText(404)))
		return
	}
	w.Header().Set("Content-Type", "application/json")
	_ = json.NewEncoder(w).Encode(result)
}

var RatingList = []Rating{
	{
		ReviewID: 1,
		Stars:    4,
	},
	{
		ReviewID: 2,
		Stars:    5,
	},
}

type Rating struct {
	ReviewID int    `json:"reviewID"`
	Stars    int    `json:"stars"`
}
