# Despliegue en AWS CloudFormation

## Prerrequisitos
- AWS CLI configurado
- Repositorio ECR creado
- VPC y 2 subredes publicas
- Secretos en AWS Secrets Manager:
  - `MONGODB_URI`
  - `JWT_SECRET`

## 1. Construir y publicar imagen
```bash
aws ecr get-login-password --region <region> | docker login --username AWS --password-stdin <account>.dkr.ecr.<region>.amazonaws.com

docker build -t btgfund:latest .
docker tag btgfund:latest <account>.dkr.ecr.<region>.amazonaws.com/btgfund:latest
docker push <account>.dkr.ecr.<region>.amazonaws.com/btgfund:latest
```

## 2. Crear stack
```bash
aws cloudformation deploy \
  --stack-name btgfund-backend \
  --template-file infra/cloudformation/btgfund-backend.yaml \
  --capabilities CAPABILITY_NAMED_IAM \
  --parameter-overrides \
    ProjectName=btgfund \
    VpcId=vpc-xxxxxxxx \
    PublicSubnet1=subnet-xxxxxxxx \
    PublicSubnet2=subnet-yyyyyyyy \
    ContainerImage=<account>.dkr.ecr.<region>.amazonaws.com/btgfund:latest \
    MongoUriSecretArn=arn:aws:secretsmanager:<region>:<account>:secret:btg/mongo \
    JwtSecretArn=arn:aws:secretsmanager:<region>:<account>:secret:btg/jwt
```

## 3. Obtener URL
```bash
aws cloudformation describe-stacks \
  --stack-name btgfund-backend \
  --query "Stacks[0].Outputs[?OutputKey=='ApiBaseUrl'].OutputValue" \
  --output text
```
