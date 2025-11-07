use moviedb

db.movies.insertMany([
  {
    name: "Movie1",
    type: "action",
    budget: 1000000,
    producer: {
      name: "producer1",
      address: "PUNE"
    }
  },
  {
    name: "Movie2",
    type: "comedy",
    budget: 500000,
    producer: {
      name: "producer2",
      address: "MUMBAI"
    }
  },
  {
    name: "Movie3",
    type: "action",
    budget: 2000000,
    producer: {
      name: "producer1",
      address: "PUNE"
    }
  }
])


db.movies.find(
  { budget: { $gt: 100000 } },
  { _id: 0, name: 1 }
)

db.movies.find(
  { "producer.address": "PUNE" },
  { _id: 0, "producer.name": 1 }
)

db.movies.updateMany(
  { type: "action" },
  { $set: { type: "horror" } }
)

db.movies.find({}, { _id: 0, name: 1, type: 1 })

db.movies.find(
  { "producer.name": "producer1" },
  { _id: 0, name: 1, "producer.address": 1 }
)
