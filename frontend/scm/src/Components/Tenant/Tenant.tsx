"use client";
import {Tenant as TenantModel} from '../../models/Tenant';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUsers} from "@fortawesome/free-solid-svg-icons";
import {useRouter} from "next/navigation";
import {useEffect, useState} from "react";

interface TenantProps {
    tenant: TenantModel;
    IdToken: string;
}

const fetchNumberContacts = async (IdToken: string, tenantUniqueName: string): Promise<number> => {
    try {
        const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/tenants/size/${tenantUniqueName}`, {
            headers: {
                'userToken': `Bearer ${IdToken}`,
            },
        });

        if (!res.ok) {
            throw new Error(`Error fetching contacts number from a tenant: ${res.statusText}`);
        }

        return await res.json();
    } catch (error) {
        console.error('Failed to fetch number of contacts:', error);
        return 0;
    }
}

const Tenant: React.FC<TenantProps> = (props) => {
    const router = useRouter();
    const [contactsNumber, setContactsNumber] = useState<number>(0);

    useEffect(() => {
        const fetch = async () => {
            const numberOfContacts = await fetchNumberContacts(props.IdToken, props.tenant.tenantUniqueName);
            setContactsNumber(numberOfContacts);
        };
        fetch();
    }, [props.IdToken, props.tenant.tenantUniqueName]);

    return (
    <div className="card cursor-pointer mx-3 shadow-xl m-7 rounded-8 hover:shadow-2xl hover:scale-105 transition"
         onClick={() => router.push(`/contacts/${props.tenant.tenantUniqueName}`)}>
        <div className="card-body">
            <h2 className="card-title">{props.tenant.title}</h2>
            <p className={"max-w-56 break-words"}>{props.tenant.description}</p>
            <p><FontAwesomeIcon className={"h-5 w-auto ml-1 mt-8 mr-2"} icon={faUsers}/>{contactsNumber}</p>
        </div>
        <figure>
            <div style={{backgroundColor: props.tenant.colorCode, height: '50px', width: '100%'}}></div>
        </figure>
    </div>
    )};

export default Tenant;