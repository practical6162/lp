use companyDutyDB


db.Empl.insertMany([
  { e_no: 123, e_name: "Sachin", post: "Manager", pay_rate: 30000 },
  { e_no: 124, e_name: "Amit", post: "Engineer", pay_rate: 25000 },
  { e_no: 125, e_name: "Ravi", post: "Manager", pay_rate: 28000 },
  { e_no: 126, e_name: "Sneha", post: "Clerk", pay_rate: 15000 },
  { e_no: 127, e_name: "Karan", post: "Engineer", pay_rate: 27000 }
])

db.Position.insertMany([
  { pos_no: 1, post: "Manager" },
  { pos_no: 2, post: "Engineer" },
  { pos_no: 3, post: "Clerk" }
])

db.Duty_alloc.insertMany([
  { pos_no: 1, e_no: 123, month: "April", year: 2003, shift: "First" },
  { pos_no: 2, e_no: 124, month: "April", year: 2003, shift: "Second" },
  { pos_no: 1, e_no: 125, month: "April", year: 2003, shift: "First" },
  { pos_no: 3, e_no: 126, month: "May", year: 2003, shift: "Second" }
])

db.Duty_alloc.find(
  { e_no: 123, shift: "First", month: "April", year: 2003 }
)


var sachinPay = db.Empl.findOne({ e_name: "Sachin" }).pay_rate;


db.Empl.find({ pay_rate: { $gte: sachinPay } })


db.createView(
  "Post_Salary_Stats",       
  "Empl",                    
  [
    {
      $group: {
        _id: "$post",
        MinSalary: { $min: "$pay_rate" },
        MaxSalary: { $max: "$pay_rate" },
        AvgSalary: { $avg: "$pay_rate" }
      }
    }
  ]
)

db.Post_Salary_Stats.find()


db.Duty_alloc.aggregate([
  {
    $lookup: {
      from: "Empl",
      localField: "e_no",
      foreignField: "e_no",
      as: "EmpInfo"
    }
  },
  { $unwind: "$EmpInfo" },
  { $match: { "EmpInfo.post": "Manager" } },
  {
    $group: {
      _id: "$shift",
      EmployeeCount: { $sum: 1 }
    }
  }
])


