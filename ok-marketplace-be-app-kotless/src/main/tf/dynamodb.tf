resource "aws_dynamodb_table" "demands" {
  name = "demands"
  billing_mode = "PROVISIONED"
  read_capacity = 20
  write_capacity = 20
  hash_key = "id"

  attribute {
    name = "id"
    type = "S"
  }

  attribute {
    name = "title"
    type = "S"
  }

  global_secondary_index {
    name = "idx_by_title"
    read_capacity = 20
    write_capacity = 20
    hash_key = "title"
    range_key = "id"
    projection_type = "ALL"
  }
}

resource "aws_dynamodb_table" "proposals" {
  name = "proposals"
  billing_mode = "PROVISIONED"
  read_capacity = 20
  write_capacity = 20
  hash_key = "id"

  attribute {
    name = "id"
    type = "S"
  }

  attribute {
    name = "title"
    type = "S"
  }

  global_secondary_index {
    name = "idx_by_title"
    read_capacity = 20
    write_capacity = 20
    hash_key = "title"
    range_key = "id"
    projection_type = "ALL"
  }
}