provider "aws" {
  region = "us-east-1"
}

module "vpc" {
  source = "terraform-aws-modules/vpc/aws"
  name = "lanchonete-vpc"
  cidr = "10.0.0.0/16"
}

module "subnets" {
  source = "terraform-aws=modules/subnet/aws"
  availability_zones = ["us-east-1a", "us-east-1b","us-east-1c"]
  vpc_id = module.vpc.vpc_id
}

module "security_groups" {
  source = "terraform-aws=modules/security-group/aws"
  vpc_id = module.vpc.vpc_id
}

resource "aws_s3_bucket" "terraform_state" {
  bucket = "lanchonete_dbb_terraform_state_bucket"
  acl = "private"
  versioning {
    enabled = true
  }
}

terraform {
  backend "s3" {
    bucket = "aws_s3_bucket.terraform_state.bucket"
    key = "enviroments/mysql-cliente/terraform.tfstate"
    region = "us-east-1"
    encrypt = true
    dynamodb_table = "terraform_lock"
  }
}

module "rds_mysql_cliente" {
  source = "terraform-aws=modules/rds/aws"
  identifier = "mysql-cliente"
  engine = "mysql"
  engine_version = "8.0"
  instance_class = "db.t3.micro"
  vpc_id = module.vpc.vpc_id
  subnet_ids = [module.subnets.private_subnet_ids[1]]
  security_group_ids = [module.security_groups.db_security_group_id]
}