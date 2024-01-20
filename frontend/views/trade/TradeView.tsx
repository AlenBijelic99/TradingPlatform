import {Button} from '@hilla/react-components/Button.js';
import {Notification} from '@hilla/react-components/Notification.js';
import {TextField} from '@hilla/react-components/TextField.js';
import {Grid} from "@hilla/react-components/Grid";
import {GridColumn} from "@hilla/react-components/GridColumn";
import {CryptoCurrencyService} from 'Frontend/generated/endpoints.js';
import {useEffect, useState} from 'react';
import CryptoCurrencyRecord
    from "Frontend/generated/ch/heigvd/application/services/CryptoCurrencyService/CryptoCurrencyRecord";

export default function TradeView() {
    const [cryptoCurrencies, setCryptoCurrencies] = useState<CryptoCurrencyRecord[]>([]);

    useEffect(() => {
        CryptoCurrencyService.getAllWithPrice().then(setCryptoCurrencies);
    });

    return (
        <>
            <section>
                <div className="p-m flex gap-m">
                    <Grid
                        items={cryptoCurrencies}>

                        <GridColumn path="name"/>
                        <GridColumn path="symbol"/>
                        <GridColumn path="price"/>
                    </Grid>
                </div>
            </section>
        </>
    );
}
