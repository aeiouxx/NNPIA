import { BaseEntity } from "../types/entities";

export function mapFieldToId<T extends BaseEntity>(field: keyof T, item: T) : T {
  return {
    ...item,
    id: item[field]
  }
}