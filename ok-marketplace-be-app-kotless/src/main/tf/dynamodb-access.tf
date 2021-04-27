data "aws_iam_policy_document" "marketplace_tables_policy" {
  statement {
    sid = "AllowRWDynamoDBTable"
    effect = "Allow"

    actions = [
      "dynamodb:DescribeTable",
      "dynamodb:GetItem",
      "dynamodb:PutItem",
      "dynamodb:Scan",
      "dynamodb:Query",
      "dynamodb:UpdateItem",
      "dynamodb:DeleteItem",
    ]

    resources = [
      "arn:aws:dynamodb:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:table/demands",
      "arn:aws:dynamodb:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:table/demands/index/*",
      "arn:aws:dynamodb:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:table/proposals",
      "arn:aws:dynamodb:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:table/proposals/index/*",
    ]
  }
}

resource "aws_iam_role_policy" "marketplace" {
  role = "${aws_iam_role.backend_app_kotless_0.name}"
  policy = "${data.aws_iam_policy_document.marketplace_tables_policy.json}"
}