import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TableheaderkeyMapperPipe } from './tableheaderkey-mapper.pipe';
import { Permission } from '../utils';

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
  @Input({ required: false }) permission: Permission | null = null;

  @Output() viewEmitterAction = new EventEmitter<string>();
  @Output() editEmitterAction = new EventEmitter<string>();
  @Output() deleteEmitterAction = new EventEmitter<string>();

  tableAction(action: number, id: string) {
    switch (action) {
      case 0:
        this.viewEmitterAction.emit(id);
        break;
      case 1:
        this.editEmitterAction.emit(id);
        break;
      case 2:
        this.deleteEmitterAction.emit(id);
        break;
    }
  }
}
