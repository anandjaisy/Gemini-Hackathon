import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'tableheaderkeyMapper',
  standalone: true,
})
export class TableheaderkeyMapperPipe implements PipeTransform {
  transform(
    value: any,
    header: string,
    headerToKeyMap: { [key: string]: string }
  ): any {
    const key = headerToKeyMap[header];
    return key ? value[key] : value[header];
  }
}
