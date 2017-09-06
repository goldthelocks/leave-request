# Details
A sample Leave Request application which uses the Flowable framework.

## Technology stack:
* Spring Boot
* Spring Data JPA
* Spring Security
* Thymeleaf
* Flowable
* MySQL (for database)

## How to run
* Create 'leaves' database
* Run as Spring Boot app
* Register users and make sure to have the following users: 'employee', 'team lead' and 'manager'

## Flow:
### Employee Leave Workflow
1. Employee requests for leave.
2. Anyone from the team lead group can approve/reject the request.
2.1. If the team lead rejects the request, the task will be returned to the employee.
2.2. If the team lead approves the request, the task will be passed onto manager's approval.
3. Anyone from the manager group can approve/reject the request.
3.1. If the manager rejects the request, the task will will be returned to the team lead who previously reviewed the request.
3.2. If the manager approves the request, the workflow ends and the leave request status will be 'Approved'.