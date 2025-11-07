use collegeDB

db.Teachers.insertMany([
  { Tname: "Amit", dno: 1, Experience: 5, Salary: 12000, Date_of_Joining: "2018-06-12" },
  { Tname: "Ravi", dno: 2, Experience: 8, Salary: 15000, Date_of_Joining: "2015-03-25" },
  { Tname: "Sneha", dno: 2, Experience: 3, Salary: 9500, Date_of_Joining: "2020-01-10" },
  { Tname: "Priya", dno: 3, Experience: 10, Salary: 18000, Date_of_Joining: "2012-09-05" }
])


db.Department.insertMany([
  { Dno: 1, Dname: "Computer" },
  { Dno: 2, Dname: "IT" },
  { Dno: 3, Dname: "ENTC" }
])


db.Students.insertMany([
  { Sname: "abc", Roll_No: 1, Class: "FE" },
  { Sname: "xyz", Roll_No: 2, Class: "SE" },
  { Sname: "pqr", Roll_No: 3, Class: "TE" },
  { Sname: "mno", Roll_No: 5, Class: "FE" }
])


db.Teachers.find(
  { dno: 2, Salary: { $gte: 10000 } }
)


db.Students.find(
  { $or: [ { Roll_No: 2 }, { Sname: "xyz" } ] }
)


db.Students.updateOne(
  { Roll_No: 5 },
  { $set: { Sname: "Updated_Student" } }
)


db.Students.deleteMany({ Class: "FE" })


db.Students.createIndex({ Roll_No: 1 })


db.Students.getIndexes()
