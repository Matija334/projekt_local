import React, { useState } from 'react';
import { MultiValue } from 'react-select';
import CreatableSelect from 'react-select/creatable';
import { toast } from 'react-toastify';

interface RemovePropsPopupProps {
    tenantUniqueName: string;
    selectedContacts: string[];
    IdToken: string;
    tenantId: string;
    availableProps: { label: string; value: string }[];
    onClose: () => void;
    onSave: () => void;
}

const RemovePropsPopup: React.FC<RemovePropsPopupProps> = ({
                                                               tenantUniqueName,
                                                               selectedContacts,
                                                               IdToken,
                                                               tenantId,
                                                               availableProps,
                                                               onClose,
                                                               onSave,
                                                           }) => {
    const [selectedProps, setSelectedProps] = useState<MultiValue<{ label: string; value: string }>>([]);

    const handlePropsChange = (newValue: MultiValue<{ label: string; value: string }>) => {
        setSelectedProps(newValue);
    };

    const handleSave = async () => {
        try {
            const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/tenants/props/multiple/remove/${tenantUniqueName}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'userToken': `Bearer ${IdToken}`,
                    'tenantId': tenantId
                },
                body: JSON.stringify({
                    contactIds: selectedContacts,
                    propsToRemove: selectedProps.map(prop => prop.value)
                })
            });

            if (!res.ok) {
                toast.error(res.statusText || 'Failed to remove props');
                return;
            }

            toast.success('Props removed successfully');
            onSave();
        } catch (error: any) {
            toast.error(error.message || 'Failed to remove props');
        }
    };

    return (
        <div className="fixed z-20 flex flex-col justify-center items-center bg-gray-500 bg-opacity-65 inset-0">
            <div className="bg-white p-8 rounded-lg shadow-lg">
                <h2 className="text-xl mb-4">Remove props from selected contacts</h2>
                <CreatableSelect
                    isMulti
                    options={availableProps}
                    value={selectedProps}
                    onChange={handlePropsChange}
                    className="mb-4"
                />
                <div className="flex justify-end">
                    <button
                        onClick={onClose}
                        className="btn px-4 btn-sm bg-red-600 border-0 text-white rounded-8 font-semibold hover:scale-105 transition hover:bg-red-700 mr-5">
                        Close popup
                    </button>
                    <button
                        onClick={handleSave}
                        className="btn px-4 btn-sm bg-primary-light border-0 text-white dark:bg-primary-dark dark:hover:bg-primary-dark rounded-8 font-semibold hover:scale-105 transition hover:bg-primary-dark">
                        Remove Props
                    </button>
                </div>
            </div>
        </div>
    );
};

export default RemovePropsPopup;