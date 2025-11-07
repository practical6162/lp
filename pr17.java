use shopdb

db.orderinfo.insertMany([
  { cust_id: 123, cust_name: "abc", status: "A", price: 250 },
  { cust_id: 124, cust_name: "xyz", status: "B", price: 300 },
  { cust_id: 125, cust_name: "pqr", status: "A", price: 500 },
  { cust_id: 126, cust_name: "mno", status: "C", price: 450 }
])


db.orderinfo.find(
  { price: { $gte: 250, $lte: 450 } },
  { _id: 0, cust_name: 1 }
)


db.orderinfo.updateOne(
  { cust_id: 123 },
  { $inc: { price: 10 } }
)


db.orderinfo.updateOne(
  { cust_id: 124 },
  { $inc: { price: -5 } }
)


db.orderinfo.updateMany(
  {},
  { $unset: { status: "" } }
)


db.orderinfo.updateOne(
  { cust_id: 123 },
  { $unset: { status: "" } }
)


db.orderinfo.find(
  { $or: [ { status: "A" }, { price: 250 } ] },
  { _id: 0, cust_name: 1 }
)
