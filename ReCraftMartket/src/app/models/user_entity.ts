import { FileDataUser } from "./file-data-user";
import { Role } from "./role";

export class UserEntity {
   id!: string;
   firstname!: string;
   lastname!: string;
   username!: string;
   password!: string;
   phonenumber!: string;
   address!: string;
   points!: number;
   role!: Role;
   fileUser!: FileDataUser;

}

