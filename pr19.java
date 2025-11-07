use Employee


db.createCollection("emp1")


db.emp1.insertMany([
  { eno: 1, ename: "Navin", address: "Pune", sal: 12000 },
  { eno: 2, ename: "Anusha", address: "Mumbai", sal: 8000 },
  { eno: 3, ename: "Ravi", address: "Nashik", sal: 18000 },
  { eno: 4, ename: "Neha", address: "Nagpur", sal: 4000 },
  { eno: 5, ename: "Karan", address: "Pune", sal: 25000 }
])


db.emp1.find().pretty()


db.emp1.find({ sal: { $gt: 5000 } })


db.emp1.find({ sal: { $lt: 15000 } })


db.emp1.find({ sal: { $gt: 10000, $lt: 20000 } })


db.emp1.updateMany(
  {},
  { $mul: { sal: 1.10 } }
)


db.emp1.deleteMany({ sal: { $lt: 5000 } })


db.emp1.renameCollection("employee1")


db.employee1.find({ ename: { $regex: /^N/i } })


db.employee1.find().sort({ ename: 1 })


use Employee1


db.dropDatabase()


use Employee


db.emp1.insert({ eno: 6, ename: "Rohit", address: "Kolhapur", sal: 15000 })


db.emp1.drop()


