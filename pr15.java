use schoolDB

db.Student.insertMany([
  { Rollno: 1, name: "Navin", subject: "DMSA", marks: 78 },
  { Rollno: 2, name: "Anusha", subject: "OSD", marks: 75 },
  { Rollno: 3, name: "Ravi", subject: "TOC", marks: 69 },
  { Rollno: 4, name: "Veena", subject: "TOC", marks: 70 },
  { Rollno: 5, name: "Pravini", subject: "OSD", marks: 80 },
  { Rollno: 6, name: "Reena", subject: "DMSA", marks: 50 },
  { Rollno: 7, name: "Geeta", subject: "CN", marks: 90 },
  { Rollno: 8, name: "Akash", subject: "CN", marks: 85 }
])


db.Student.aggregate([
  {
    $group: {
      _id: "$subject",
      MaxMarks: { $max: "$marks" }
    }
  }
])


db.Student.aggregate([
  {
    $group: {
      _id: "$subject",
      MinMarks: { $min: "$marks" }
    }
  }
])


db.Student.aggregate([
  {
    $group: {
      _id: "$subject",
      TotalMarks: { $sum: "$marks" }
    }
  }
])


db.Student.aggregate([
  {
    $group: {
      _id: "$subject",
      AvgMarks: { $avg: "$marks" }
    }
  }
])


db.Student.aggregate([
  { $sort: { Rollno: 1 } },
  {
    $group: {
      _id: "$subject",
      FirstRecord: { $first: "$$ROOT" }
    }
  }
])


db.Student.aggregate([
  { $sort: { Rollno: 1 } },
  {
    $group: {
      _id: "$subject",
      LastRecord: { $last: "$$ROOT" }
    }
  }
])


db.Student.aggregate([
  {
    $group: {
      _id: "$subject",
      Count: { $sum: 1 }
    }
  }
])


db.Student.aggregate([
  {
    $group: {
      _id: "$subject",
      Count: { $sum: 1 }
    }
  }
])

