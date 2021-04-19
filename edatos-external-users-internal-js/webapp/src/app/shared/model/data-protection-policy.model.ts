import { BaseVersionedAndAuditingEntity } from "arte-ng/model";
import { InternationalString } from "./international-string.model";

export class DataProtectionPolicy extends BaseVersionedAndAuditingEntity {
    
    public value: InternationalString;
}
