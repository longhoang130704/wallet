# Non-required function

Avaibility: zero downtime, high performence

Security: confidentiality, integrity, availability

Scalability: monitoring service, scale up and down by Kubenertes, Grafana

Resilience: restart new node similiar to old node

Redundancy: replica database, backup and restore 1 times per week

# Impact feature
Onboard user with User-Agent

Payment process - MFA authentication

Distributed Transaction - 2 phase commit - data consistency

System fault - lock account - protect user balance, data  

# Business logic

Onboard user, process payment

# System design image - one node

![Mô tả hình ảnh](https://res.cloudinary.com/db9aue3uu/image/upload/v1745035574/one_node_eyszou.png)

System deploy need 3 EC2 instances:

DatabaseService deploy on EC2 AWS

Wallet service deploy on EC2 AWS

Database service deploy on EC2 AWS

# System design image - multi node

![Mô tả hình ảnh](https://res.cloudinary.com/db9aue3uu/image/upload/v1743994496/wallet_system_design_vvivmj.png)

System deploy need 3 EC2 instances:

DatabaseService deploy on EC2 AWS

Wallet service deploy on EC2 AWS

Database service deploy on EC2 AWS