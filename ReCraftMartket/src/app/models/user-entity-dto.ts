import { Role } from "./role";

export class UserEntityDto {
    id!: string;
  firstname!: string;
  lastname!: string;
  username!: string;
  role!: Role; 
  address!: string;
  points!: number;
}
