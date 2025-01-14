provider "aws" {
    region = "us-east-1"
}
resource "aws_instance" "spring_boot_app" {
    ami           = "ami-0c02fb55956c7d316"
    instance_type = "t2.micro"
    key_name = "my-key-pair"
    security_groups = [aws_security_group.allow_http.name]
    user_data = <<-EOF
        #!/bin/bash
        sudo yum update -y
        sudo yum install java-17-amazon-corretto -y
        wget https://s3.amazonaws.com/mycoursebucket1/my-app.jar
        java -jar my-app.jar
    EOF
}

resource "aws_security_group" "allow_http" {
    name        = "allow_http"
    description = "Allow HTTP traffic"
    ingress {
        from_port   = 80
        to_port     = 80
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
    egress {
        from_port   = 0
        to_port     = 0
        protocol    = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }
}

