provider "aws" {
  region = "us-east-1"
}

module "vpc" {
  source = "terraform-aws-modules/vpc/aws"
  name = "lanchonete-vpc"
  cidr = "10.0.0.0/16"
}

resource "aws_subnet" "subnet" {
  count = 3
  cidr_block = element(["10.0.1.0/24", "10.0.2.0/24", "10.0.3.0/24"], count.index)
  availability_zone = element(["us-east-1a", "us-east-1b","us-east-1c"], count.index)
  vpc_id = module.vpc.vpc_id
  map_public_ip_on_launch = true
  tags = {
    Name = "subnet-${count.index + 1}"
  }
}

module "security_groups" {
  source = "terraform-aws-modules/security-group/aws"
  vpc_id = module.vpc.vpc_id
}



terraform {
  backend "s3" {
    bucket = "lanchonete-dbb-terraform-state-bucket"
    key = "enviroments/mysql-cliente/terraform.tfstate"
    region = "us-east-1"
    encrypt = true
    dynamodb_table = "terraform_lock"
  }
}

resource "aws_s3_bucket" "terraform_state" {
  bucket = "lanchonete-dbb-terraform-state-bucket"
}

resource "aws_db_instance" "mysql_cliente" {
  identifier = "mysql-cliente"
  engine = "mysql"
  engine_version = "8.0"
  instance_class = "db.t3.micro"
  allocated_storage = 20
  storage_type = "gp2"
  username = "admin"
  password = "admin"
  db_subnet_group_name = "subnet"
  vpc_security_group_ids = [module.security_groups.db_security_group_id]
  multi_az = false
  publicly_accessible = true
  subnet_group_name = "aws_db_subnet_group.subnet.name"
  tags = {
    Name = "mysql-cliente"
  }
}

resource "aws_db_subnet_group" "subnet" {
  name = "db-subnet-group"
  description = "Subnet Group"
  subnet_ids = [aws_subnet.subnet[*].id]
}
