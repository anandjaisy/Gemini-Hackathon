import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TableheaderkeyMapperPipe } from './tableheaderkey-mapper.pipe';

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [TableheaderkeyMapperPipe],
  templateUrl: './table.component.html',
  styleUrl: './table.component.scss',
})
export class TableComponent {
  @Input({ required: true }) headers: string[] = [];
  @Input({ required: false }) dataSource: any[] = [];
  @Input({ required: true }) headerToKeyMap: any = {};
  @Output() editEmitterAction = new EventEmitter<string>();
  @Output() deleteEmitterAction = new EventEmitter<string>();

  tableAction(action: number, id: string) {
    switch (action) {
      case 0:
        this.editEmitterAction.emit(id);
        break;
      case 1:
        this.deleteEmitterAction.emit(id);
        break;
    }
  }
}
