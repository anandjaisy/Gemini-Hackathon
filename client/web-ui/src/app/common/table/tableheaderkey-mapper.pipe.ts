import { DatePipe } from '@angular/common';
import { Inject, LOCALE_ID, Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'tableheaderkeyMapper',
  standalone: true,
})
export class TableheaderkeyMapperPipe implements PipeTransform {
  private datePipe: DatePipe;
  constructor(@Inject(LOCALE_ID) locale: string) {
    this.datePipe = new DatePipe(locale);
  }
  transform(
    value: any,
    header: string,
    headerToKeyMap: { [key: string]: string }
  ): any {
    const key = headerToKeyMap[header];
    const finalValue = key ? this.resolvePath(value, key) : value[header];
    // Check if the value is a date and format it
    if (this.isDate(finalValue)) {
      return this.datePipe.transform(finalValue, 'medium');
    }
    return finalValue;
  }

  private resolvePath(obj: any, path: string): any {
    return path.split('.').reduce((acc, part) => acc && acc[part], obj);
  }

  private isDate(value: any): boolean {
    return value instanceof Date;
  }
}
