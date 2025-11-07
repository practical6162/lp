use companyDB


db.Emp.insertMany([
  { emp_id: 1, ename: "Amit", street: "MG Road", city: "Pune" },
  { emp_id: 2, ename: "Ravi", street: "JM Road", city: "Mumbai" },
  { emp_id: 3, ename: "Sneha", street: "FC Road", city: "Pune" }
])

db.Company.insertMany([
  { c_id: 101, cname: "ABC", city: "Mumbai" },
  { c_id: 102, cname: "Mbank", city: "Delhi" },
  { c_id: 103, cname: "Bosch", city: "Pune" },
  { c_id: 104, cname: "SBC", city: "Chennai" }
])

db.Manager.insertMany([
  { mgr_id: 1, mgrname: "Raj" },
  { mgr_id: 2, mgrname: "Karan" }
])

db.Works.insertMany([
  { emp_id: 1, c_id: 101, ename: "Amit", cname: "ABC", sal: 18000 },
  { emp_id: 2, c_id: 102, ename: "Ravi", cname: "Mbank", sal: 25000 },
  { emp_id: 3, c_id: 103, ename: "Sneha", cname: "Bosch", sal: 30000 },
  { emp_id: 4, c_id: 104, ename: "Meera", cname: "SBC", sal: 55000 }
])

db.Company.updateOne(
  { cname: "ABC" },
  { $set: { city: "Pune" } }
)

db.Works.updateMany(
  { cname: "Mbank", sal: { $lte: 20000 } },
  { $mul: { sal: 1.10 } }
)

db.Works.updateMany(
  { cname: "Mbank", sal: { $gt: 20000 } },
  { $mul: { sal: 1.03 } }
)

db.Works.find(
  { cname: "Bosch" },
  { _id: 0, ename: 1 }
)


db.Company.find({ cname: "Bosch", city: "Pune" })

db.Works.find({ cname: "Bosch" }, { _id: 0, ename: 1 })


db.Works.deleteMany(
  { cname: "SBC", sal: { $gt: 50000 } }
)


