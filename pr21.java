use orgDB


db.Employee.insertMany([
  { employee_name: "Amit", street: "MG Road", city: "Pune" },
  { employee_name: "Ravi", street: "FC Road", city: "Mumbai" },
  { employee_name: "Sneha", street: "JM Road", city: "Delhi" },
  { employee_name: "Karan", street: "MG Road", city: "Pune" },
  { employee_name: "Neha", street: "Park Street", city: "Delhi" }
])

db.Works.insertMany([
  { employee_name: "Amit", company_name: "First Bank Corporation", salary: 12000 },
  { employee_name: "Ravi", company_name: "Small Bank Corporation", salary: 9000 },
  { employee_name: "Sneha", company_name: "First Bank Corporation", salary: 15000 },
  { employee_name: "Karan", company_name: "TechSoft", salary: 8000 },
  { employee_name: "Neha", company_name: "First Bank Corporation", salary: 11000 }
])

db.Company.insertMany([
  { company_name: "First Bank Corporation", city: "Pune" },
  { company_name: "Small Bank Corporation", city: "Mumbai" },
  { company_name: "TechSoft", city: "Delhi" }
])

db.Manages.insertMany([
  { employee_name: "Amit", manager_name: "Karan" },
  { employee_name: "Ravi", manager_name: "Sneha" },
  { employee_name: "Sneha", manager_name: "Neha" },
  { employee_name: "Karan", manager_name: "Amit" },
  { employee_name: "Neha", manager_name: "Sneha" }
])


db.Works.find(
  { company_name: "First Bank Corporation" },
  { _id: 0, employee_name: 1 }
)


db.Works.aggregate([
  { $match: { company_name: "First Bank Corporation" } },
  {
    $lookup: {
      from: "Employee",
      localField: "employee_name",
      foreignField: "employee_name",
      as: "EmployeeInfo"
    }
  },
  { $unwind: "$EmployeeInfo" },
  { $project: { _id: 0, employee_name: 1, city: "$EmployeeInfo.city" } }
])


db.Works.aggregate([
  { $match: { company_name: "First Bank Corporation", salary: { $gt: 10000 } } },
  {
    $lookup: {
      from: "Employee",
      localField: "employee_name",
      foreignField: "employee_name",
      as: "EmployeeInfo"
    }
  },
  { $unwind: "$EmployeeInfo" },
  {
    $project: {
      _id: 0,
      employee_name: 1,
      street: "$EmployeeInfo.street",
      city: "$EmployeeInfo.city",
      salary: 1
    }
  }
])


db.Works.aggregate([
  {
    $lookup: {
      from: "Company",
      localField: "company_name",
      foreignField: "company_name",
      as: "CompanyInfo"
    }
  },
  { $unwind: "$CompanyInfo" },
  {
    $lookup: {
      from: "Employee",
      localField: "employee_name",
      foreignField: "employee_name",
      as: "EmployeeInfo"
    }
  },
  { $unwind: "$EmployeeInfo" },
  {
    $match: {
      $expr: { $eq: ["$EmployeeInfo.city", "$CompanyInfo.city"] }
    }
  },
  { $project: { _id: 0, employee_name: 1, "EmployeeInfo.city": 1, "CompanyInfo.city": 1 } }
])



db.Manages.aggregate([
  {
    $lookup: {
      from: "Employee",
      localField: "employee_name",
      foreignField: "employee_name",
      as: "Emp"
    }
  },
  { $unwind: "$Emp" },
  {
    $lookup: {
      from: "Employee",
      localField: "manager_name",
      foreignField: "employee_name",
      as: "Mgr"
    }
  },
  { $unwind: "$Mgr" },
  {
    $match: {
      $expr: {
        $and: [
          { $eq: ["$Emp.city", "$Mgr.city"] },
          { $eq: ["$Emp.street", "$Mgr.street"] }
        ]
      }
    }
  },
  { $project: { _id: 0, employee_name: "$Emp.employee_name", manager_name: 1 } }
])



db.Works.find(
  { company_name: { $ne: "First Bank Corporation" } },
  { _id: 0, employee_name: 1, company_name: 1 }
)



db.Works.updateMany(
  { company_name: "First Bank Corporation" },
  { $mul: { salary: 1.10 } }
)



db.Works.deleteMany({ company_name: "Small Bank Corporation" })
