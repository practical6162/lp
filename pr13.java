use department

db.teacher.insertMany([
  { name: "Amit", department: "Computer", experience: 5, salary: 40000 },
  { name: "Ravi", department: "IT", experience: 8, salary: 55000 },
  { name: "Sneha", department: "Computer", experience: 3, salary: 35000 },
  { name: "Priya", department: "ENTC", experience: 6, salary: 50000 },
  { name: "Karan", department: "IT", experience: 10, salary: 60000 }
])


db.teacher.aggregate([
  {
    $group: {
      _id: "$department",
      avg_salary: { $avg: "$salary" }
    }
  }
])



db.teacher.aggregate([
  {
    $group: {
      _id: "$department",
      no_of_employees: { $sum: 1 }
    }
  }
])



db.teacher.aggregate([
  {
    $group: {
      _id: "$department",
      min_salary: { $min: "$salary" }
    }
  }
])



db.teacher.createIndex({ department: 1 })


db.teacher.getIndexes()


db.teacher.dropIndex({ department: 1 })


db.teacher.dropIndex("department_1")
