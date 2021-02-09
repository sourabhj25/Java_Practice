import { NgModule } from '@angular/core';
import { CommonModule } from "@angular/common";

import { searchFilter } from "./searchFilter.pipe";

@NgModule({
    declarations: [searchFilter],
    imports: [CommonModule],
    exports: [searchFilter]
})

export class searchFilterPipe { }