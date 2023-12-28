export class TransactionGeneratorDto {
  client!: number;
  count!: number;
  percentage!: number; // country percentage
  targetCountry!: string;
  origin!: string;
  type!: string;
  originPercentage!: number;
}
