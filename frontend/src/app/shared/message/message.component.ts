import { Component, Input } from '@angular/core';
import { NgModel } from '@angular/forms';

@Component({
  selector: 'app-message',
  template: `
    <div *ngIf="hasError()" class="p-message p-message-error">
        {{ text }}
    </div>
  `,
  styles: [`
    .p-message {
        margin-top: 0.25em!important;
        padding: 0.25em!important;
    }
  `]
})
export class MessageComponent {

    @Input() error: string;
    @Input() model: NgModel;
    @Input() text: string;

    hasError(): boolean {
        const control = this.model.control;
        return control.hasError(this.error) && control.dirty;
    }

}
