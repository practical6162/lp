use shopdb

db.orderinfo.insertMany([
  { cust_id: 123, cust_name: "abc", status: "A", price: 250 },
  { cust_id: 124, cust_name: "xyz", status: "A", price: 500 },
  { cust_id: 125, cust_name: "pqr", status: "B", price: 1200 },
  { cust_id: 126, cust_name: "mno", status: "A", price: 750 }
])

db.orderinfo.aggregate([
  { $match: { status: "A" } },
  { $group: { _id: "$cust_id", avg_price: { $avg: "$price" } } }
])

db.orderinfo.find(
  { price: { $gte: 100, $lte: 1000 } },
  { status: 1, _id: 0 }
)

db.orderinfo.find({}, { _id: 0 })

db.orderinfo.createIndex({ cust_id: 1 })

db.orderinfo.getIndexes()

db.orderinfo.find({ cust_id: 123 }).explain("executionStats")
